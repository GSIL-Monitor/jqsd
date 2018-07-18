package com.jqsd.common.tool;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Record;
import com.jqsd.common.model.DbConfig;
import com.jqsd.common.util.ConvertUtils;
import com.jqsd.common.util.DesUtil;
import com.jqsd.common.util.StrUtil;
import com.jqsd.common.vo.CertificateVo;

import kingdee.bos.webapi.client.ApiClient;
import kingdee.bos.webapi.client.K3CloudApiClient;

/**
 * Created by sam on 12/27/17.
 */
public class KingdeeTool {

	static Log log = Log.getLog(KingdeeTool.class);

	static String K3CloudURL = "http://aijqsd.ik3cloud.com/K3Cloud/";
	static String dbId = "20170719204231";// 测试2
	// static String dbId = "5a12fe5ed097d0";//大兴集团
	static String uid = "Administrator";
	//static String uid = "接口测试";
	static String pwd = "jqsd.333";
	static int lang = 2052;

	public static K3CloudApiClient login(String url, String dbId, String name, String pwd) {
		K3CloudApiClient client = new K3CloudApiClient(url);

		try {
			if (client.login(dbId, name, pwd, lang)) {
				return client;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	public static K3CloudApiClient login(String dbId) {
		DbConfig db = DbConfig.dao.findById(dbId);
		return login(db.getUrl(), db.getDbId(), db.getUserName(), DesUtil.decrypt(db.getPwd()));
	}

	public static K3CloudApiClient login() {
		return login(K3CloudURL, dbId, uid, pwd);
	}

	private static boolean login(ApiClient client) throws Exception {
		return client.login(dbId, uid, pwd, lang);
	}

	public static void saveOutHead() {

	}

	/**
	 * 格式化结果
	 *
	 * @param res
	 * @returnl
	 */
	private static Map<String, String> getResult(K3CloudApiClient client, String sFormId, String res) throws Exception {
		Map<String, Object> map = ConvertUtils.formJson(res);
		Map<String, Object> result = (Map<String, Object>) map.get("Result");
		Map<String, Object> mm = (Map<String, Object>) result.get("ResponseStatus");
		Map<String, String> m = Maps.newHashMap();
		m.put("success", mm.get("IsSuccess") + "");

		if (m.get("success").equals("true")) {

			m.put("number", result.get("Number") + "");
			m.put("id", ConvertUtils.convertObjToInt(result.get("Id")) + "");

			// submit(client, sFormId, m.get("number"));
			// audit(client, sFormId, m.get("number"));
			return m;
		} else {
			List<Map<String, String>> list = (List<Map<String, String>>) mm.get("Errors");
			String Message = list.get(0).get("FieldName");
			if (StrUtil.isBlank(Message)) {
				Message = list.get(0).get("Message");
			}

			m.put("msg", Message);
			return m;
		}
	}

	/**
	 * 保存
	 *
	 * @param formId
	 * @param content
	 * @throws Exception
	 */
	public static String save(K3CloudApiClient client, String formId, String content) throws Exception {
		String sResult = client.save(formId, content);
		log.info("CurrencyTest success:" + sResult);
		return sResult;
	}

	/**
	 * 批量保存
	 *
	 * @param formId
	 * @param content
	 * @throws Exception
	 */
	public static void batchSave(String formId, String content) throws Exception {
		K3CloudApiClient client = new K3CloudApiClient(K3CloudURL);
		Boolean result = login(client);
		if (result) {
			String sResult = client.batchSave(formId, content);
			System.out.println("CurrencyTest success:" + sResult);
		}
	}

	/**
	 * 审核
	 *
	 * @param formId
	 * @param number
	 * @throws Exception
	 */
	public static String audit(K3CloudApiClient client, String formId, String number) throws Exception {
		String subContent = "{\"CreateOrgId\":\"0\",\"Numbers\":[\"" + number + "\"]}";
		String sResult = client.audit(formId, subContent);
		// System.out.println("CurrencyTest success:" + sResult);
		return sResult;
	}

	/**
	 * 反审
	 *
	 * @param formId
	 * @param content
	 * @throws Exception
	 */
	public static void unAudit(String formId, String content) throws Exception {
		K3CloudApiClient client = new K3CloudApiClient(K3CloudURL);
		Boolean result = login(client);
		if (result) {
			String sResult = client.unAudit(formId, content);
			System.out.println("CurrencyTest success:" + sResult);
		}
	}

	/**
	 * 删除
	 *
	 * @param formId
	 * @param content
	 * @throws Exception
	 */
	public static void delete(String formId, String content) throws Exception {
		K3CloudApiClient client = new K3CloudApiClient(K3CloudURL);
		Boolean result = login(client);
		if (result) {
			String sResult = client.delete(formId, content);
			System.out.println("CurrencyTest success:" + sResult);
		}
	}

	/**
	 * 提交
	 *
	 * @param formId
	 * @param number
	 * @throws Exception
	 */
	public static String submit(K3CloudApiClient client, String formId, String number) throws Exception {
		String subContent = "{\"CreateOrgId\":\"0\",\"Numbers\":[\"" + number + "\"]}";
		String sResult = client.submit(formId, subContent);
		// System.out.println("CurrencyTest success:" + sResult);
		return sResult;
	}

	/**
	 * 查询
	 *
	 * @param formId
	 * @param content
	 * @throws Exception
	 */
	public static void view(String formId, String content) throws Exception {
		K3CloudApiClient client = new K3CloudApiClient(K3CloudURL);
		Boolean result = login(client);
		if (result) {
			String sResult = client.view(formId, content);
			System.out.println("CurrencyTest success:" + sResult);
		}
	}

	/**
	 * 草稿
	 *
	 * @param formId
	 * @param content
	 * @throws Exception
	 */
	public static void draft(String formId, String content) throws Exception {
		K3CloudApiClient client = new K3CloudApiClient(K3CloudURL);
		Boolean result = login(client);
		if (result) {
			String sResult = client.draft(formId, content);
			System.out.println("CurrencyTest success:" + sResult);
		}
	}

	/**
	 * 销售订单
	 * 
	 * @param client
	 * @param orgId
	 * @param credit
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> SaleOrderApi(K3CloudApiClient client, String orgId, CertificateVo credit,
			List<Record> list) throws Exception {

		String sFormId = "SAL_SaleOrder";

		String detailJson = "";

		for (Record record : list) {

			detailJson += "{\"FEntryID\":null,\"FReturnType\":\" \",\"FMapId\":{\"FNumber\":\"\"},\"FMaterialId\":{\"FNumber\":\""
					+ record.getStr("productCode") + "\"},"// 物料编码
					+ "\"FBarcode\":\"" + record.getStr("productCode") + "\","// 商品条码
					+ "\"FUnitID\":{\"FNumber\":\"" + record.getStr("mainUnitName") + "\"},\"FInventoryQty\":0.0,"
					+ "\"FCurrentInventory\":0.0,\"FAwaitQty\":0.0,\"FAvailableQty\":0.0,\"FQty\":"+ record.getStr("mainCount") + ","// 销售数量
					+ "\"FOldQty\":\"\"," + "\"FPrice\":" + record.getStr("price") + ",\"FTaxPrice\":\"\","//易订货没有不含税价格
					+ "\"FIsFree\":false,\"FTaxCombination\":{\"FNumber\":\"\"},"
					+ "\"FEntryTaxRate\":16.00,"//税率默认为16%
					+ "\"FProduceDate\":null,\"FExpPeriod\":0,\"FExpUnit\":\" \",\"FExpiryDate\":null,"
					+ "\"FLot\":{\"FNumber\":\"\"},\"FDiscountRate\":0.0,\"FDeliveryDate\":\""+credit.getCreateTime()+"\","
					+ "\"FStockOrgId\":{\"FNumber\":\"101\"},\"FBranchId\":{\"FNumber\":\"\"},\"FSettleOrgIds\":{\"FNumber\":\"101\"},"
					+ "\"FSupplyOrgId\":{\"FNumber\":\"101\"},\"FOwnerTypeId\":\"BD_Supplier\",\"FOwnerId\":{\"FNumber\":\"0007\"},"
					+ "\"FEntryNote\":null,\"FReserveType\":\"1\",\"FPriority\":0,\"FMtoNo\":null,\"FPromotionMatchType\":\" \",\"FNetOrderEntryId\":0,"
					+ "\"FPriceBaseQty\":"+ record.getStr("mainCount") + ",\"FSetPriceUnitID\":{\"FNumber\":\"\"},\"FStockUnitID\":{\"FNumber\":\"Pcs\"},"
					+ "\"FStockQty\":"+ record.getStr("mainCount") + ",\"FStockBaseQty\":"+ record.getStr("mainCount") + ",\"FServiceContext\":null,"
					+ "\"FOUTLMTUNIT\":\"SAL\",\"FOutLmtUnitID\":{\"FNumber\":\""+ record.getStr("mainUnitName") + "\"},"
					+ "\"F_kd_Amount\":5500.00,"//门店供货价字段暂时不写
					+ "\"FRetailSaleProm\":false,\"FOrderEntryPlan\":[{\"FDetailID\":null,"
					+ "\"FDetailLocId\":{\"FNUMBER\":\"\"},\"FDetailLocAddress\":null,\"FPlanDate\":\"" + credit.getCreateTime() + "\","
					+ "\"FTransportLeadTime\":0,\"FPlanQty\":"+ record.getStr("mainCount") + ",\"FNOTICEQTY\":0.0,\"FNOTICEREMAINQTY\":0.0,"
					+ "\"FNOTICEBASEQTY\":0.0,\"FNOTICEREMAINBASEQTY\":0.0}]}";
			if (list.size() > 1) {
				detailJson += ",";
			}

		}

		if (list.size() > 1) {
			detailJson = detailJson.substring(0, detailJson.length() - 1);
		}

		String sContent = "{\"Creator\":\"\",\"NeedUpDateFields\":[],\"NeedReturnFields\":[],"
				+ "\"IsDeleteEntry\":\"True\",\"SubSystemId\":\"\",\"IsVerifyBaseDataField\":\"false\","
				+ "\"Model\":{\"FID\":\"0\",\"FBillTypeID\":{\"FNumber\":\"XSDD08_SYS\"},"
				+ "\"FBillNo\":\"\",\"FDate\":\"" + credit.getCreateTime() + "\"," // 日期
				+ "\"FSaleOrgId\":{\"FNumber\":\"101\"}," // 销售组织
				+ "\"FCustId\":{\"FNumber\":\"" + credit.getCustomer() + "\"}," // 客户
				+ "\"FHeadDeliveryWay\":{\"FNumber\":\"\"},"
				+ "\"FReceiveId\":{\"FNumber\":\"" + credit.getCustomer() + "\"},\"FHEADLOCID\":{\"FNUMBER\":\"\"},"
				+ "\"FCorrespondOrgId\":{\"FNumber\":\"\"},\"FSaleDeptId\":{\"FNumber\":\"BM000028\"},"
				+ "\"FSaleGroupId\":{\"FNumber\":\"\"},\"FSalerId\":{\"FNumber\":\"092\"}," // 销售员
				+ "\"FLinkPhone\":\"\",\"FLinkMan\":\"\",\"FReceiveAddress\":\"\"," + "\"FSettleId\":{\"FNumber\":\""
				+ credit.getCustomer() + "\"}," + "\"FReceiveContact\":{\"FName\":\"\"},"
				+ "\"FChargeId\":{\"FNumber\":\"" + credit.getCustomer() + "\"},"
				+ "\"FNetOrderBillNo\":\"\",\"FNetOrderBillId\":0,"
				+ "\"FOppID\":0,\"FSalePhaseID\":{\"FNumber\":\"\"},\"FISINIT\":false,"
				+ "\"FNote\":\"\",\"FIsMobile\":false,\"F_kd_Remarks\":\"\",\"F_kd_Combo\":\"0\","
				+ "\"FSaleOrderFinance\":{\"FSettleCurrId\":{\"FNumber\":\"PRE001\"}," // 结算币别
				+ "\"FRecConditionId\":{\"FNumber\":\"\"},"
				+ "\"FIsPriceExcludeTax\":true,\"FSettleModeId\":{\"FNumber\":\"\"},\"FIsIncludedTax\":true,"
				+ "\"FPriceListId\":{\"FNumber\":\"\"},\"FDiscountListId\":{\"FNumber\":\"\"},"
				+ "\"FBillTaxAmount\":0.0,\"FBillAmount\":0.0,\"FBillAllAmount\":0.0,"
				+ "\"FExchangeTypeId\":{\"FNumber\":\"HLTX01_SYS\"},\"FExchangeRate\":1.0}," + "\"FSaleOrderEntry\":["+ detailJson + "],"
				+ "\"FSaleOrderPlan\":[{\"FEntryID\":null,\"FNeedRecAdvance\":false,\"FReceiveType\":{\"FNumber\":\"\"},"
				+ "\"FRecAdvanceRate\":100.0,\"FRecAdvanceAmount\":0.0,\"FMustDate\":null,\"FRelBillNo\":null,"
				+ "\"FRecAmount\":0.0,\"FControlSend\":\" \",\"FReMark\":null,"
				+ "\"FPlanMaterialId\":{\"FNumber\":\"\"},\"FMaterialSeq\":0,\"FOrderEntryId\":0}]}}";

		String sRes = save(client, sFormId, sContent);
		return getResult(client, sFormId, sRes);

	}

	/**
	 * 退货通知单
	 * 
	 * @param client
	 * @param orgId
	 * @param credit
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> ReturnNoticeApi(K3CloudApiClient client, String orgId, CertificateVo credit,
			List<Record> list) throws Exception {

		String sFormId = "SAL_RETURNNOTICE";

		String detailJson = "";

		for (Record record : list) {

			detailJson += "{\"FENTRYID\":null,\"FMapId\":{\"FNumber\":\"\"},"
					+ "\"FMaterialId\":{\"FNumber\":\"" + record.getStr("productCode") + "\"},"// 商品条码
					+ "\"FUnitID\":{\"FNumber\":\"" + record.getStr("mainUnitName") + "\"},"//	销售计量单位名称
					+ "\"FInventoryQty\":0.0,"
					+ "\"FQty\":"+ record.getStr("mainCount") + ","//	销售数量
					+ "\"FPRODUCEDATE\":null,\"FExpiryDate\":null,\"FLot\":{\"FNumber\":\"\"},"
					+ "\"FPriceBaseQty\":"+ record.getStr("mainCount") + ",\"FASEUNITID\":{\"FNumber\":\"" + record.getStr("mainUnitName") + "\"},"
					+ "\"FDeliverydate\":\""+ credit.getCreateTime() + "\",\"FStockId\":{\"FNumber\":\"\"},"
					+ "\"FBOMId\":{\"FNumber\":\"\"},\"FMtoNo\":null,"
					+ "\"FEntryDescription\":null,\"FRmType\":{\"FNumber\":\"THLX01_SYS\"},"
					+ "\"FIsReturnCheck\":false,\"FCheckQty\":0.0,\"FBaseCheckQty\":0.0,"
					+ "\"FQualifiedQty\":0.0,\"FBaseQualifiedQty\":0.0,\"FUnqualifiedQty\":0.0,"
					+ "\"FBaseUnqualifiedQty\":0.0,\"FJunkedQty\":0.0,\"FBaseJunkedQty\":0.0,"
					+ "\"FJoinCheckQty\":0.0,\"FBaseJoinCheckQty\":0.0,\"FJoinQualifiedQty\":0.0,"
					+ "\"FBaseJoinQualifiedQty\":0.0,\"FJoinJunkedQty\":0.0,\"FBaseJoinJunkedQty\":0.0,"
					+ "\"FJoinUnqualifiedQty\":0.0,\"FBaseJoinUnqualifiedQty\":0.0,"
					+ "\"FStockUnitID\":{\"FNumber\":\"" + record.getStr("mainUnitName") + "\"},\"FStockQty\":"+ record.getStr("mainCount") + ","
					+ "\"FStockBaseQty\":"+ record.getStr("mainCount") + ",\"FOwnerTypeID\":\"BD_OwnerOrg\","
					+ "\"FOwnerID\":{\"FNumber\":\"101\"},\"FSOEntryId\":0,\"FRefuseFlag\":false}";
			if (list.size() > 1) {
				detailJson += ",";
			}
		}
		
		if (list.size() > 1) {
			detailJson = detailJson.substring(0, detailJson.length() - 1);
		}

		/*String sContent = "{\"Creator\":\"\",\"NeedUpDateFields\":[],\"NeedReturnFields\":[],"
				+ "\"IsDeleteEntry\":\"True\",\"SubSystemId\":\"\",\"IsVerifyBaseDataField\":\"false\","
				+ "\"Model\":{\"FID\":\"0\",\"FBillTypeID\":{\"FNumber\":\"THTZD01_SYS\"},"
				+ "\"FBillNo\":\"\",\"FDate\":\""+credit.getCreateTime()+"\",\"FSaleOrgId\":{\"FNumber\":\"104\"},"
				+ "\"FRetcustId\":{\"FNumber\":\"" + credit.getCustomer() + "\"},"//	退货客户
				+ "\"FSaledeptid\":{\"FNumber\":\"\"},"
				+ "\"FReturnReason\":{\"FNumber\":\"\"},\"FHeadLocId\":{\"FNUMBER\":\"\"},"
				+ "\"FCorrespondOrgId\":{\"FNumber\":\"\"},\"FSaleGroupId\":{\"FNumber\":\"\"},"
				+ "\"FSalesManId\":{\"FNumber\":\"\"},\"FRetorgId\":{\"FNumber\":\"104\"},"
				+ "\"FRetDeptId\":{\"FNumber\":\"\"},\"FStockerGroupId\":{\"FNumber\":\"\"},"
				+ "\"FStockerId\":{\"FName\":\"\"},\"FDescription\":\"\","
				+ "\"FReceiveCusId\":{\"FNumber\":\"" + credit.getCustomer() + "\"},\"FReceiveAddress\":\"\","
				+ "\"FSettleCusId\":{\"FNumber\":\"" + credit.getCustomer() + "\"},\"FReceiveCusContact\":{\"FName\":\"\"},"
				+ "\"FPayCusId\":{\"FNumber\":\"" + credit.getCustomer() + "\"},\"FOwnerTypeIdHead\":\"BD_OwnerOrg\","
				+ "\"FOwnerIdHead\":{\"FNumber\":\"\"},\"SubHeadEntity\":{\"FSettleCurrId\":{\"FNumber\":\"PRE001\"},"//结算币别  默认为人民币
				+ "\"FSettleOrgId\":{\"FNumber\":\"104\"},\"FChageCondition\":{\"FNumber\":\"\"},"
				+ "\"FSettleTypeId\":{\"FNumber\":\"\"},\"FLocalCurrId\":{\"FNumber\":\"PRE001\"},"
				+ "\"FExchangeTypeId\":{\"FNumber\":\"HLTX01_SYS\"},\"FExchangeRate\":1.0}," + "\"FEntity\":["+ detailJson + "]}}";*/
		
		String sContent = "{\"Creator\":\"\",\"NeedUpDateFields\":[],\"NeedReturnFields\":[],"
				+ "\"IsDeleteEntry\":\"True\",\"SubSystemId\":\"\",\"IsVerifyBaseDataField\":\"false\","
				+ "\"Model\":{\"FID\":\"0\",\"FBillTypeID\":{\"FNumber\":\"THTZD01_SYS\"},"
				+ "\"FBillNo\":\"\",\"FDate\":\""+credit.getCreateTime()+"\",\"FSaleOrgId\":{\"FNumber\":\"101\"},"
				+ "\"FRetcustId\":{\"FNumber\":\"" + credit.getCustomer() + "\"},\"FSaledeptid\":{\"FNumber\":\"\"},"
				+ "\"FReturnReason\":{\"FNumber\":\"\"},\"FHeadLocId\":{\"FNUMBER\":\"\"},"
				+ "\"FCorrespondOrgId\":{\"FNumber\":\"\"},\"FSaleGroupId\":{\"FNumber\":\"\"},"
				+ "\"FSalesManId\":{\"FNumber\":\"\"},\"FRetorgId\":{\"FNumber\":\"101\"},"
				+ "\"FRetDeptId\":{\"FNumber\":\"\"},\"FStockerGroupId\":{\"FNumber\":\"\"},"
				+ "\"FStockerId\":{\"FName\":\"\"},\"FDescription\":\"\",\"FReceiveCusId\":{\"FNumber\":\"" + credit.getCustomer() + "\"},"
				+ "\"FReceiveAddress\":\"\",\"FSettleCusId\":{\"FNumber\":\"" + credit.getCustomer() + "\"},"
				+ "\"FReceiveCusContact\":{\"FName\":\"\"},\"FPayCusId\":{\"FNumber\":\"" + credit.getCustomer() + "\"},"
				+ "\"FOwnerTypeIdHead\":\"BD_OwnerOrg\",\"FOwnerIdHead\":{\"FNumber\":\"\"},"
				+ "\"SubHeadEntity\":{\"FSettleCurrId\":{\"FNumber\":\"PRE001\"},"
				+ "\"FSettleOrgId\":{\"FNumber\":\"101\"},\"FChageCondition\":{\"FNumber\":\"\"},\"FSettleTypeId\":{\"FNumber\":\"\"},"
				+ "\"FLocalCurrId\":{\"FNumber\":\"PRE001\"},\"FExchangeTypeId\":{\"FNumber\":\"HLTX01_SYS\"},"
				+ "\"FExchangeRate\":1.0}," + "\"FEntity\":["+ detailJson + "]}}";

		String sRes = save(client, sFormId, sContent);
		return getResult(client, sFormId, sRes);

	}

}
