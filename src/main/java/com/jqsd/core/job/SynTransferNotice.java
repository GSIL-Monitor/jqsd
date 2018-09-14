package com.jqsd.core.job;

import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.google.common.collect.Maps;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jqsd.common.AppConfig;
import com.jqsd.common.constant.CommonConstant;
import com.jqsd.common.model.JobLog;
import com.jqsd.common.model.ScheduleJob;
import com.jqsd.common.tool.KingdeeTool;
import com.jqsd.common.util.StrUtil;
import com.jqsd.common.vo.CertificateVo;

import kingdee.bos.webapi.client.K3CloudApiClient;

public class SynTransferNotice implements Job {
	Log log = Log.getLog(this.getClass());

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		// TODO Auto-generated method stub
		// get param from job
		Map data = jobExecutionContext.getJobDetail().getJobDataMap();

		long jobId = Long.parseLong(data.get("jobId") + "");
		String name = data.get("name") + "";
		String dbId = data.get("dbId") + "";

		if (!ScheduleJob.dao.startJob(jobId)) {
			log.info("Warning:上一个任务还在执行中！");
			JobLog.dao.saveLog(jobId, name, "上一个任务还在执行中！", CommonConstant.JOB_FAIL);// 记录任务日志
			return;
		}

		K3CloudApiClient client = KingdeeTool.login(dbId);

		if (client == null) {
			JobLog.dao.saveLog(jobId, name, "kingdee service error!", CommonConstant.JOB_FAIL);// 记录任务日志
		} else {
			// 查询所有中间表,未同步的销售订单
			List<Record> list = findList();
			list.stream().forEach(record -> Db.tx(() -> {
				String res;
				try {
					res = doJob(client, record, jobId, name);
					log.info(res);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return true;
			}));
		}

		ScheduleJob.dao.stopJob(jobId);// 记录任务最后执行时间
		log.info(name + "执行完成!");
	}

	private String doJob(K3CloudApiClient client, Record record, long jobId, String name) throws Exception {
		String res = CommonConstant.SYNC_SUCC;
		String dbid = record.getStr("dbid");
		String type = record.getStr("type");
		String orderNum = record.getStr("orderNum");
		String orgId = "100";
		if (StrUtil.isNotBlank(orderNum) && StrUtil.isNotBlank(type)) {
			Map<String, String> map = syncApiPur(client, orgId, record);

			if ("true".equals(map.get("success"))) {
				//Db.update("update uploadOrderLogs set content=? where dbid = ?",res, dbid);
				JobLog.dao.saveLog(jobId, name, res, CommonConstant.JOB_SUCC);// 记录任务日志
				// 同步成功后修改对应订单的状态
				Db.update("update uploadOrder set sync_status=? where orderNum = ?",1, orderNum);

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else if ("NotWhole".equals(map.get("msg"))) {
				//	代表非整体联营商 
			} else {
				res = "同步失败," + map.get("msg") + ",orderNum:" + orderNum;

				Db.update("update uploadOrder set sync_status=? where orderNum = ?",2, orderNum);
				JobLog.dao.saveLog(jobId, name, res, CommonConstant.JOB_FAIL);// 记录任务日志
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		} else {
			res = "同步失败，订单编号为空或单据类型为空";
			Db.update("update uploadOrder set sync_status=? where orderNum = ?",2, orderNum);
			JobLog.dao.saveLog(jobId, name, res, CommonConstant.JOB_FAIL);// 记 录任务日志
		}

		return name;

	}

	private Map<String, String> syncApiPur(K3CloudApiClient client, String orgId, Record record) throws Exception {

		KingdeeTool kingdeeTool= new KingdeeTool();
		
		CertificateVo cvo = new CertificateVo();
		cvo.setSellerId(record.getStr("sellerId"));
		cvo.setType(record.getStr("type"));
		// cvo.setDeliveryDate(record.getStr("deliveryDate"));
		cvo.setCustomer(record.getStr("customer"));
		// cvo.setProductCode(record.getStr("productCode"));
		// cvo.setBarCode(record.getStr("barCode"));
		// cvo.setMainUnitName(record.getStr("mainUnitName"));
		cvo.setCreateTime(record.getStr("createTime"));
		// cvo.setBeforeTexMoney(record.getStr("beforeTexMoney"));
		// cvo.setAfterTexMoney(record.getStr("afterTexMoney"));
		// cvo.setMainCount(record.getStr("mainCount"));
		cvo.setOrderNum(record.getStr("orderNum"));

		Map<String, String> map = null;
		
		String biliType =kingdeeTool.BillType(client, cvo.getCustomer());

		try {
			List<Record> list = orderDetailList(cvo.getOrderNum());

			if (list.size() <= 0) {
				Map<String, String> m = Maps.newHashMap();
				m.put("msg", "订单" + cvo.getOrderNum() + "没有查询到对应的订单详情");
				return m;
			} else {
				if ("Whole".equals(biliType)) {
					map = KingdeeTool.TransferNoticeApi(client, orgId, cvo, list);
				}else {
					Map<String, String> m = Maps.newHashMap();
					m.put("msg", "NotWhole");
					return m;
				}
			}

			return map;
		} catch (Exception e) {
			log.info("Error:" + e.toString());
			Map<String, String> m = Maps.newHashMap();
			m.put("msg", e.getMessage());
			return m;
		}

	}

	// 查询出所有未同步的订单
	private List<Record> findList() {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.dbid,a.orderNum AS orderNum, a.type AS type,a.createTime AS createTime,");
		sql.append(" a.custCode AS customer,a.sellerId AS sellerId");
		sql.append(" FROM uploadOrder a where a.type=1 AND a.sync_status=0");//	LYKH01绍兴店是调拨通知单(整店联营商)，LYKH02东莞店是销售订单(非整店联营商)

		return Db.find(sql.toString());

	}

	// 查询出对应订单的订单详情
	private List<Record> orderDetailList(String orderNum) {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT b.orderNum AS orderNum,b.beforeTexMoney AS beforeTexMoney,");
		sql.append("b.mainCount AS mainCount,b.afterTexMoney AS afterTexMoney ,b.productCode AS productCode");
		sql.append(",b.barCode AS barCode,b.mainUnitName AS mainUnitName");
		sql.append(",b.marketPrice AS marketPrice,b.originPrice AS originPrice,b.price AS price,b.mainPrice AS mainPrice");
		sql.append(" FROM uploadOrderDetail b WHERE orderNum=" + "'" + orderNum + "'");

		return Db.find(sql.toString());
	}

	/**
	 * 测试任务
	 * 
	 * @param args
	 * @throws JobExecutionException
	 */
	public static void main(String[] args) throws JobExecutionException {
		AppConfig.init();

		SynTransferNotice job = new SynTransferNotice();

		// 查询所有中间表,未同步的调拨通知单
		List<Record> list = job.findList();
		K3CloudApiClient client = KingdeeTool.login();
		list.stream().forEach(record -> Db.tx(() -> {
			try {
				job.doJob(client, record, 1, "同步调拨通知单");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}));

	}

}
