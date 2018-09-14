package com.jqsd.common.tool;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jqsd.common.model.DbConfig;
import com.jqsd.common.util.ConvertUtils;
import com.jqsd.common.util.DesUtil;
import com.jqsd.common.util.StrUtil;
import com.jqsd.common.vo.CertificateVo;

import kingdee.bos.json.JSONObject;
import kingdee.bos.webapi.client.ApiClient;
import kingdee.bos.webapi.client.K3CloudApiClient;

/**
 * Created by sam on 12/27/17.
 */
public class KingdeeTool {

	static Log log = Log.getLog(KingdeeTool.class);

	 static String K3CloudURL = "http://aijqsd.ik3cloud.com/K3Cloud/"; // 正式
	//static String K3CloudURL = "http://210239v5n9.iask.in/k3cloud/"; // 测试
	//static String dbId = "5b7a8c6bbfca05";//
	 static String dbId = "20170719204231";//	测试账套DBID
	 //static String uid = "Administrator";
	 static String uid = "kingdee";
	 //static String pwd = "jqsd.555";
	static String pwd = "jqsd.123";
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

			 submit(client, sFormId, m.get("number"));
			 audit(client, sFormId, m.get("number"));
			return m;
		} else {
			List<Map<String, String>> list = (List<Map<String, String>>) mm.get("Errors");
			String FieldName = list.get(0).get("FieldName");
			String Message = list.get(0).get("Message");
			if (StrUtil.isBlank(Message)) {
				Message = list.get(0).get("Message");
			}

			m.put("msg", FieldName + "," + Message);
			return m;
		}
	}

	/**
	 * 保存1
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
	 * 查询客户，通过客户分类以及联营商分类确定类型
	 * 
	 * @param client
	 * @param formId
	 * @param number
	 * @return
	 * @throws Exception
	 */
	public static String BillType(K3CloudApiClient client, String number) throws Exception {

		String formId = "BD_Customer";

		String sql = "{\"CreateOrgId\":\"0\",\"Number\":\"" + number + "\",\"Id\":\"\"}";

		String sRes = client.view(formId, sql);

		Map<String, Object> map = ConvertUtils.formJson(sRes);
		Map<String, Object> result = (Map<String, Object>) map.get("Result");

		Map<String, Object> ResponseStatus = (Map<String, Object>) result.get("ResponseStatus");

		if (ResponseStatus != null) {

			return "";
		}

		Map<String, Object> result2 = (Map<String, Object>) result.get("Result");
		Map<String, Object> F_kd_Assistant = (Map<String, Object>) result2.get("F_kd_Assistant");

		if (F_kd_Assistant != null) {

			List<Map<String, Object>> FDataValue = (ArrayList<Map<String, Object>>) F_kd_Assistant.get("FDataValue");
			if (FDataValue.size() >= 1) {
				String CustomerType = (String) FDataValue.get(0).get("Value");

				if ("联营商".equals(CustomerType)) {
					Map<String, Object> F_kd_Assistant1 = (Map<String, Object>) result2.get("F_kd_Assistant1");
					if (F_kd_Assistant1 != null) {

						List<Map<String, Object>> FDataValues = (ArrayList<Map<String, Object>>) F_kd_Assistant1
								.get("FDataValue");
						String Assistant1Type = (String) FDataValues.get(0).get("Value");
						if ("整店联营商".equals(Assistant1Type)) {

							return "Whole"; // 代表代表整店联营商
						} else {
							return "NotWhole"; // 代表代表非整店联营商
						}
					} else {
						return "";
					}
				} else {
					return "";
				}
			} else {
				return "";
			}

		} else {

			return "";
		}
	}

	/**
	 * 
	 * 通过商品编号查询出对应的供应商编号
	 * 
	 * @param client
	 * @param number
	 * @return
	 * @throws Exception
	 */
	public static String Supplier(K3CloudApiClient client, String number) throws Exception {

		String formId = "BD_MATERIAL";

		String sql = "{\"CreateOrgId\":\"0\",\"Number\":\"" + number + "\",\"Id\":\"\"}";

		String sRes = client.view(formId, sql);

		Map<String, Object> map = ConvertUtils.formJson(sRes);
		Map<String, Object> result = (Map<String, Object>) map.get("Result");

		Map<String, Object> ResponseStatus = (Map<String, Object>) result.get("ResponseStatus");

		if (ResponseStatus != null) {

			return "";
		}
		Map<String, Object> result2 = (Map<String, Object>) result.get("Result");
		List<Map<String, Object>> MaterialPurchase = (ArrayList<Map<String, Object>>) result2.get("MaterialPurchase");

		Map<String, Object> DefaultVendor = (Map<String, Object>) MaterialPurchase.get(0).get("DefaultVendor");

		String Number = (String) DefaultVendor.get("Number");

		return Number;

	}
	
	// 通过客户编号查询对应的组织库存关系
	private static Record OrganizationWarehouse(String custCode,Integer type) {

			StringBuffer sql = new StringBuffer();
			sql.append("SELECT top 1 * FROM t_yf_Organization_Warehouse WHERE custCode=" + "'" + custCode + "' and type =" + "" + type + "");

			return Db.find(sql.toString()).get(0);
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

		String billType = BillType(client,credit.getCustomer());

		String FBillTypeID = "XSDD08_SYS";
		
		Record record2 = OrganizationWarehouse(credit.getCustomer(),1);
		
		String organization1 = record2.getStr("organization1");
		String warehouse1 = record2.getStr("warehouse1");
		String organization2 = record2.getStr("organization2");
		String warehouse2 = record2.getStr("warehouse2");
		String FSalerId = record2.getStr("FSalerId");

		BigDecimal bd2 = new BigDecimal("1.16");
		for (Record record : list) {

			BigDecimal bd = new BigDecimal(record.getStr("price"));
			BigDecimal FTaxPrice = bd.divide(bd2,6,BigDecimal.ROUND_HALF_UP);

			String supplier = Supplier(client, record.getStr("productCode"));

			detailJson += "{\"FEntryID\":null,\"FReturnType\":\" \",\"FMapId\":{\"FNumber\":\"\"},\"FMaterialId\":{\"FNumber\":\""
					+ record.getStr("productCode") + "\"},"// 物料编码
					+ "\"FBarcode\":\"" + record.getStr("productCode") + "\","// 商品条码
					+ "\"FUnitID\":{\"FNumber\":\"" + record.getStr("mainUnitName") + "\"},\"FInventoryQty\":0.0,"
					+ "\"FCurrentInventory\":0.0,\"FAwaitQty\":0.0,\"FAvailableQty\":0.0,\"FQty\":"
					+ record.getStr("mainCount") + ","// 销售数量
					+ "\"FOldQty\":\"\"," + "\"FPrice\":" + FTaxPrice + ",\"FTaxPrice\":\"" + record.getStr("price")
					+ "\","// 易订货没有不含税价格
					+ "\"FIsFree\":false,\"FTaxCombination\":{\"FNumber\":\"\"}," + "\"FEntryTaxRate\":16.00,"// 税率默认为16%
					+ "\"FProduceDate\":null,\"FExpPeriod\":0,\"FExpUnit\":\" \",\"FExpiryDate\":null,"
					+ "\"FLot\":{\"FNumber\":\"\"},\"FDiscountRate\":0.0,\"FDeliveryDate\":\"" + credit.getCreateTime()
					+ "\","
					+ "\"FStockOrgId\":{\"FNumber\":\""+organization1+"\"},\"FBranchId\":{\"FNumber\":\"\"},\"FSettleOrgIds\":{\"FNumber\":\""+organization1+"\"},"
					+ "\"FSupplyOrgId\":{\"FNumber\":\""+organization1+"\"}," + "\"FOwnerTypeId\":\"BD_Supplier\","
					+ "\"FOwnerId\":{\"FNumber\":\"" + supplier + "\"},"
					+ "\"FEntryNote\":null,\"FReserveType\":\"1\",\"FPriority\":0,\"FMtoNo\":null,\"FPromotionMatchType\":\" \",\"FNetOrderEntryId\":0,"
					+ "\"FPriceBaseQty\":" + record.getStr("mainCount")
					+ ",\"FSetPriceUnitID\":{\"FNumber\":\"\"},\"FStockUnitID\":{\"FNumber\":\"Pcs\"},"
					+ "\"FStockQty\":" + record.getStr("mainCount") + ",\"FStockBaseQty\":" + record.getStr("mainCount")
					+ ",\"FServiceContext\":null," + "\"FOUTLMTUNIT\":\"SAL\",\"FOutLmtUnitID\":{\"FNumber\":\""
					+ record.getStr("mainUnitName") + "\"},"
					// + "\"F_kd_Amount\":5500.00,"//门店供货价字段暂时不写
					+ "\"FRetailSaleProm\":false,\"FOrderEntryPlan\":[{\"FDetailID\":null,"
					+ "\"FDetailLocId\":{\"FNUMBER\":\"\"},\"FDetailLocAddress\":null,\"FPlanDate\":\""
					+ credit.getCreateTime() + "\"," + "\"FTransportLeadTime\":0,\"FPlanQty\":"
					+ record.getStr("mainCount") + ",\"FNOTICEQTY\":0.0,\"FNOTICEREMAINQTY\":0.0,"
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
				+ "\"Model\":{\"FID\":\"0\",\"FBillTypeID\":{\"FNumber\":\"" + FBillTypeID + "\"},"
				+ "\"FBillNo\":\"\",\"FDate\":\"" + credit.getCreateTime() + "\"," // 日期
				+ "\"FSaleOrgId\":{\"FNumber\":\""+organization1+"\"}," // 销售组织
				+ "\"FCustId\":{\"FNumber\":\"" + credit.getCustomer() + "\"}," // 客户
				+ "\"FHeadDeliveryWay\":{\"FNumber\":\"\"}," + "\"FReceiveId\":{\"FNumber\":\"" + credit.getCustomer()
				+ "\"},\"FHEADLOCID\":{\"FNUMBER\":\"\"},"
				+ "\"FCorrespondOrgId\":{\"FNumber\":\"\"},\"FSaleDeptId\":{\"FNumber\":\"\"},"
				+ "\"FSaleGroupId\":{\"FNumber\":\"\"},\"FSalerId\":{\"FNumber\":\""+FSalerId+"\"}," // 销售员 客户不做 放这里
				+ "\"FLinkPhone\":\"\",\"FLinkMan\":\"\",\"FReceiveAddress\":\"\"," + "\"FSettleId\":{\"FNumber\":\""
				+ credit.getCustomer() + "\"}," + "\"FReceiveContact\":{\"FName\":\"\"},"
				+ "\"FChargeId\":{\"FNumber\":\"" + credit.getCustomer() + "\"},"
				+ "\"FNetOrderBillNo\":\"\",\"FNetOrderBillId\":0,"
				+ "\"FOppID\":0,\"FSalePhaseID\":{\"FNumber\":\"\"},\"FISINIT\":false,"
				+ "\"FNote\":\"\",\"FIsMobile\":false,\"F_kd_Remarks\":\"\",\"F_kd_Combo\":\"0\","
				+ "\"F_kd_Base\":{\"FNumber\":\"\"},\"F_orderNum\":\""+credit.getOrderNum()+"\","
				+ "\"FSaleOrderFinance\":{\"FSettleCurrId\":{\"FNumber\":\"PRE001\"}," // 结算币别
				+ "\"FRecConditionId\":{\"FNumber\":\"\"},"
				+ "\"FIsPriceExcludeTax\":true,\"FSettleModeId\":{\"FNumber\":\"\"},\"FIsIncludedTax\":true,"
				+ "\"FPriceListId\":{\"FNumber\":\"\"},\"FDiscountListId\":{\"FNumber\":\"\"},"
				+ "\"FBillTaxAmount\":0.0,\"FBillAmount\":0.0,\"FBillAllAmount\":0.0,"
				+ "\"FExchangeTypeId\":{\"FNumber\":\"HLTX01_SYS\"},\"FExchangeRate\":1.0}," + "\"FSaleOrderEntry\":["
				+ detailJson + "],"
				+ "\"FSaleOrderPlan\":[{\"FEntryID\":null,\"FNeedRecAdvance\":false,\"FReceiveType\":{\"FNumber\":\"\"},"
				+ "\"FRecAdvanceRate\":100.0,\"FRecAdvanceAmount\":0.0,\"FMustDate\":null,\"FRelBillNo\":null,"
				+ "\"FRecAmount\":0.0,\"FControlSend\":\" \",\"FReMark\":null,"
				+ "\"FPlanMaterialId\":{\"FNumber\":\"\"},\"FMaterialSeq\":0,\"FOrderEntryId\":0}]}}";

		String sRes = save(client, sFormId, sContent);
		
		
		return getResult(client, sFormId, sRes);

	}

	/**
	 * 退货通知单		LYKH02东莞店   非整体联营店是退货通知单
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
		
		String billType = BillType(client, credit.getCustomer());
		
		Record record2 = OrganizationWarehouse(credit.getCustomer(),2);
		
		String organization1 = record2.getStr("organization1");
		String warehouse1 = record2.getStr("warehouse1");
		String organization2 = record2.getStr("organization2");
		String warehouse2 = record2.getStr("warehouse2");
		
		if ("NotWhole".equals(billType)) {
			String detailJson = "";

			BigDecimal bd2 = new BigDecimal("1.16");
			for (Record record : list) {

				String supplier = Supplier(client, record.getStr("productCode"));
				
				BigDecimal bd = new BigDecimal(record.getStr("price"));
				BigDecimal FTaxPrice = bd.divide(bd2,6,BigDecimal.ROUND_HALF_UP);
				
				detailJson +="{\"FENTRYID\":null,\"FMapId\":{\"FNumber\":\"\"},\"FMaterialId\":{\"FNumber\":\"" + record.getStr("productCode") + "\"},"// 商品条码
						+ "\"FUnitID\":{\"FNumber\":\"" + record.getStr("mainUnitName") + "\"},"//	销售计量单位名称   + "\"FPrice\":" + record.getStr("price") + ",\"FTaxPrice\":\"" + FTaxPrice+","//	销售数量
						+ "\"FInventoryQty\":0.0,\"FQty\":"+ record.getStr("mainCount") + ",\"FPrice\":\""+FTaxPrice+"\",\"FTaxPrice\":\""+record.getStr("price")+"\","
						+ "\"FPRODUCEDATE\":null,\"FExpiryDate\":null,\"FLot\":{\"FNumber\":\"\"},"
						+ "\"FPriceBaseQty\":"+ record.getStr("mainCount") + ",\"FASEUNITID\":{\"FNumber\":\"" + record.getStr("mainUnitName") + "\"},"
						+ "\"FDeliverydate\":\""+ credit.getCreateTime() + "\",\"FStockId\":{\"FNumber\":\""+warehouse1+"\"},"//武汉仓
						+ "\"FBOMId\":{\"FNumber\":\"\"},\"FMtoNo\":null,\"FEntryDescription\":null,"
						+ "\"FRmType\":{\"FNumber\":\"THLX01_SYS\"},\"FIsReturnCheck\":false,\"FCheckQty\":0.0,"
						+ "\"FBaseCheckQty\":0.0,\"FQualifiedQty\":0.0,\"FBaseQualifiedQty\":0.0,\"FUnqualifiedQty\":0.0,"
						+ "\"FBaseUnqualifiedQty\":0.0,\"FJunkedQty\":0.0,\"FBaseJunkedQty\":0.0,\"FJoinCheckQty\":0.0,"
						+ "\"FBaseJoinCheckQty\":0.0,\"FJoinQualifiedQty\":0.0,\"FBaseJoinQualifiedQty\":0.0,"
						+ "\"FJoinJunkedQty\":0.0,\"FBaseJoinJunkedQty\":0.0,\"FJoinUnqualifiedQty\":0.0,"
						+ "\"FBaseJoinUnqualifiedQty\":0.0,\"FStockUnitID\":{\"FNumber\":\"" + record.getStr("mainUnitName") + "\"},"
						+ "\"FStockQty\":"+ record.getStr("mainCount") + ",\"FStockBaseQty\":"+ record.getStr("mainCount") + ",\"FOwnerTypeID\":\"BD_Supplier\","
						+ "\"FOwnerID\":{\"FNumber\":\""+supplier+"\"},\"FSOEntryId\":0,\"FRefuseFlag\":false}";
				if (list.size() > 1) {
					detailJson += ",";
				}
			}
			
			if (list.size() > 1) {
				detailJson = detailJson.substring(0, detailJson.length() - 1);
			}

			
			String sContent = "{\"Creator\":\"\",\"NeedUpDateFields\":[],\"NeedReturnFields\":[],"
					+ "\"IsDeleteEntry\":\"True\",\"SubSystemId\":\"\",\"IsVerifyBaseDataField\":\"false\","
					+ "\"Model\":{\"FID\":\"0\",\"FBillTypeID\":{\"FNumber\":\"THTZD01_SYS\"},"
					+ "\"FBillNo\":\"\",\"FDate\":\""+credit.getCreateTime()+"\",\"FSaleOrgId\":{\"FNumber\":\""+organization1+"\"},"
					+ "\"FRetcustId\":{\"FNumber\":\"" + credit.getCustomer() + "\"},\"FSaledeptid\":{\"FNumber\":\"\"},"
					+ "\"FReturnReason\":{\"FNumber\":\"\"},\"FHeadLocId\":{\"FNUMBER\":\"\"},"
					+ "\"FCorrespondOrgId\":{\"FNumber\":\"\"},\"FSaleGroupId\":{\"FNumber\":\"\"},"
					+ "\"FSalesManId\":{\"FNumber\":\"\"},\"FRetorgId\":{\"FNumber\":\""+organization1+"\"},"
					+ "\"FRetDeptId\":{\"FNumber\":\"\"},\"FStockerGroupId\":{\"FNumber\":\"\"},"
					+ "\"FStockerId\":{\"FName\":\"\"},\"FDescription\":\"\",\"FReceiveCusId\":{\"FNumber\":\"" + credit.getCustomer() + "\"},"
					+ "\"FReceiveAddress\":\"\",\"FSettleCusId\":{\"FNumber\":\"" + credit.getCustomer() + "\"},"
					+ "\"FReceiveCusContact\":{\"FName\":\"\"},\"FPayCusId\":{\"FNumber\":\"" + credit.getCustomer() + "\"},"
					+ "\"FOwnerTypeIdHead\":\"BD_Supplier\",\"FOwnerIdHead\":{\"FNumber\":\"\"},\"F_orderNum\":\""+credit.getOrderNum()+"\","
					+ "\"SubHeadEntity\":{\"FSettleCurrId\":{\"FNumber\":\"PRE001\"},"
					+ "\"FSettleOrgId\":{\"FNumber\":\""+organization1+"\"},\"FChageCondition\":{\"FNumber\":\"\"},\"FSettleTypeId\":{\"FNumber\":\"\"},"
					+ "\"FLocalCurrId\":{\"FNumber\":\"PRE001\"},\"FExchangeTypeId\":{\"FNumber\":\"HLTX01_SYS\"},"
					+ "\"FExchangeRate\":1.0}," + "\"FEntity\":["+ detailJson + "]}}";
			
			String sRes = save(client, sFormId, sContent);
			return getResult(client, sFormId, sRes);
			
		}else if("Whole".equals(billType)) {
			
			Map<String, String> transferNoticeApi = RetreatTransferNoticeApi(client,orgId,credit,list);
			
			return transferNoticeApi;
			
		}	
		
		return null;
	}

	/**
	 * 调拨通知单
	 * 
	 * @param client
	 * @param orgId
	 * @param credit
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> TransferNoticeApi(K3CloudApiClient client, String orgId, CertificateVo credit,
			List<Record> list) throws Exception {

		String sFormId = "PAEZ_TransferNotice";

		String detailJson = "";
		
		Record record2 = OrganizationWarehouse(credit.getCustomer(),1);
		
		String organization1 = record2.getStr("organization1");
		String warehouse1 = record2.getStr("warehouse1");
		String organization2 = record2.getStr("organization2");
		String warehouse2 = record2.getStr("warehouse2");

		for (Record record : list) {

			detailJson += "{\"FEntryID\":null,\"F_PAEZ_Base\":{\"FNumber\":\"" + record.getStr("productCode") + "\"},"
					+ "\"F_PAEZ_Qty\":" + record.getStr("mainCount") + ",\"F_PAEZ_Base1\":{\"FNumber\":\""+warehouse1+"\"}," // 调出仓库
					+ "\"F_PAEZ_Flex\":{\"F_PAEZ_FLEX__FF100001\":{\"FNUMBER\":\"\"}},"
					+ "\"F_PAEZ_Base2\":{\"FNumber\":\""+warehouse2+"\"}," // 调入仓库
					+ "\"F_PAEZ_Flex1\":{\"F_PAEZ_FLEX1__FF100001\":{\"FNUMBER\":\"\"}}," + "\"F_PAEZ_Remarks\":\"\"}"; // 备注

			if (list.size() > 1) {
				detailJson += ",";
			}
		}

		if (list.size() > 1) {
			detailJson = detailJson.substring(0, detailJson.length() - 1);
		}

		String sContent = "{\"Creator\":\"\",\"NeedUpDateFields\":[],\"NeedReturnFields\":[],"
				+ "\"IsDeleteEntry\":\"True\",\"SubSystemId\":\"\",\"IsVerifyBaseDataField\":\"false\","
				+ "\"Model\":{\"FID\":\"0\",\"FBillNo\":\"\",\"FBIZTYPE\":\"VMI\","
				+ "\"FTRANSFERDIRECT\":\"GENERAL\",\"FTRANSFERBIZTYPE\":\"OverOrgPurVMI\","
				+ "\"FSTOCKOUTORGID\":{\"FNumber\":\""+organization1+"\"},"// 默认调出组织是总部101
				+ "\"FOWNERTYPEOUTIDHead\":\"BD_OwnerOrg\","
				+ "\"FOWNEROUTIDHead\":{\"FNumber\":\""+organization1+"\"},\"FSTOCKORGID\":{\"FNumber\":\""+organization2+"\"},"// 调入库存组织 暂时默认为107
				+ "\"FOWNERTYPEINIDHead\":\"BD_OwnerOrg\",\"FOWNERINIDHead\":{\"FNumber\":\""+organization2+"\"},"
				+ "\"FSTOCKERID\":{\"FNumber\":\"\"},\"FSTOCKERGROUPID\":{\"FNumber\":\"\"},\"FDATE\":\""
				+ credit.getCreateTime() + "\","
				+ "\"FNOTE\":\"\",\"FBASECURRID\":{\"FNumber\":\"\"},\"FCreatorId\":{\"FUserID\":561051},\"F_orderNum\":\""+credit.getOrderNum()+"\"," // 创建人
				+ "\"FCreateDate\":\"\"," // 创建时间
				+ "\"FModifierId\":{\"FUserID\":\"\"}," + "\"FModifyDate\":\"\"," // 最后修改日期
				+ "\"FAPPROVERID\":{\"FUserID\":\"\"},\"FAPPROVEDATE\":\"\","// 审核日期
				+ "\"FCANCELLERID\":{\"FUserID\":\"\"},\"FCANCELDATE\":\"\","// 作废日期
				+ "\"FISINTERLEGALPERSON\":false," + "\"FEntity\":[" + detailJson + "]}}";

		String sRes = save(client, sFormId, sContent);
		return getResult(client, sFormId, sRes);

	}

	/**
	 * 退货调拨通知单 LYKH01绍兴店 整体联营店是调拨通知单
	 * 
	 * @param client
	 * @param orgId
	 * @param credit
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> RetreatTransferNoticeApi(K3CloudApiClient client, String orgId,
			CertificateVo credit, List<Record> list) throws Exception {

		String sFormId = "PAEZ_TransferNotice";

		String detailJson = "";
		
		Record record2 = OrganizationWarehouse(credit.getCustomer(),2);
		
		String organization1 = record2.getStr("organization1");
		String warehouse1 = record2.getStr("warehouse1");
		String organization2 = record2.getStr("organization2");
		String warehouse2 = record2.getStr("warehouse2");

		for (Record record : list) {

			detailJson += "{\"FEntryID\":null,\"F_PAEZ_Base\":{\"FNumber\":\"" + record.getStr("productCode") + "\"},"
					+ "\"F_PAEZ_Qty\":" + record.getStr("mainCount") + ",\"F_PAEZ_Base1\":{\"FNumber\":\""+warehouse1+"\"}," // 调出仓库
					+ "\"F_PAEZ_Flex\":{\"F_PAEZ_FLEX__FF100001\":{\"FNUMBER\":\"\"}},"
					+ "\"F_PAEZ_Base2\":{\"FNumber\":\""+warehouse2+"\"}," // 调入仓库
					+ "\"F_PAEZ_Flex1\":{\"F_PAEZ_FLEX1__FF100001\":{\"FNUMBER\":\"\"}}," + "\"F_PAEZ_Remarks\":\"\"}"; // 备注

			if (list.size() > 1) {
				detailJson += ",";
			}
		}

		if (list.size() > 1) {
			detailJson = detailJson.substring(0, detailJson.length() - 1);
		}

		String sContent = "{\"Creator\":\"\",\"NeedUpDateFields\":[],\"NeedReturnFields\":[],"
				+ "\"IsDeleteEntry\":\"True\",\"SubSystemId\":\"\",\"IsVerifyBaseDataField\":\"false\","
				+ "\"Model\":{\"FID\":\"0\",\"FBillNo\":\"\",\"FBIZTYPE\":\"VMI\","
				+ "\"FTRANSFERDIRECT\":\"RETURN\","// 调拨方向：退货(RETURN),普通(GENERAL)
				+ "\"FTRANSFERBIZTYPE\":\"OverOrgPurVMI\"," + "\"FSTOCKOUTORGID\":{\"FNumber\":\""+organization1+"\"},"// 默认调出组织是总部107
				+ "\"FOWNERTYPEOUTIDHead\":\"BD_OwnerOrg\","
				+ "\"FOWNEROUTIDHead\":{\"FNumber\":\""+organization1+"\"},\"FSTOCKORGID\":{\"FNumber\":\""+organization2+"\"},"// 调入库存组织 暂时默认为101
				+ "\"FOWNERTYPEINIDHead\":\"BD_OwnerOrg\",\"FOWNERINIDHead\":{\"FNumber\":\""+organization2+"\"},"
				+ "\"FSTOCKERID\":{\"FNumber\":\"\"},\"FSTOCKERGROUPID\":{\"FNumber\":\"\"},\"FDATE\":\""
				+ credit.getCreateTime() + "\","
				+ "\"FNOTE\":\"\",\"FBASECURRID\":{\"FNumber\":\"\"},\"FCreatorId\":{\"FUserID\":561051},\"F_orderNum\":\""+credit.getOrderNum()+"\"," // 创建人
				+ "\"FCreateDate\":\"\"," // 创建时间
				+ "\"FModifierId\":{\"FUserID\":\"\"}," + "\"FModifyDate\":\"\"," // 最后修改日期
				+ "\"FAPPROVERID\":{\"FUserID\":\"\"},\"FAPPROVEDATE\":\"\","// 审核日期
				+ "\"FCANCELLERID\":{\"FUserID\":\"\"},\"FCANCELDATE\":\"\","// 作废日期
				+ "\"FISINTERLEGALPERSON\":false," + "\"FEntity\":[" + detailJson + "]}}";

		String sRes = save(client, sFormId, sContent);
		return getResult(client, sFormId, sRes);

	}

}
