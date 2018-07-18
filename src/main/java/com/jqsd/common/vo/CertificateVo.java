package com.jqsd.common.vo;

import javax.print.DocFlavor.STRING;

/**
 * 借方model Created by sam on 1/9/18.
 */
public class CertificateVo {
	private final static String DEFAULT = "";

	private String supplier = DEFAULT;// 供应商
	private String department = DEFAULT;// 部门
	private String customer = DEFAULT;// 客户
	private String empInfo = DEFAULT;// 员工
	private String material = DEFAULT;// 物料
	private String expense = DEFAULT;// 费用
	private String assetType = DEFAULT;// 资产类型
	private String bankAcnt = DEFAULT;// 银行帐号
	private String organizations = DEFAULT;// 组织
	private String currency = DEFAULT;// 币别
	private String operatorView = DEFAULT;// 运营商视图
	private String amount = DEFAULT;// 金额
	private String explanation = DEFAULT;// 说明
	private String accountId = DEFAULT;// 科目ID
	private String AccountBillNO = DEFAULT;// DS_BILL
	private boolean isDebit = false;
	private boolean isCredit = false;

	private String FDate = DEFAULT; // 日期
	private String sellerId = DEFAULT; // 业务员ID
	private String productCode = DEFAULT; // 商品编码
	private String mainUnitName = DEFAULT; // 销售计量单位名称
	private String barCode = DEFAULT; // 商品条码
	private String deliveryDate = DEFAULT; // 发货日期
	private String type = DEFAULT; // 单据类型
	private String createTime = DEFAULT; // 创建时间
	private String beforeTexMoney = DEFAULT; // 税前价
	private String afterTexMoney = DEFAULT; // 税后价
	private String mainCount = DEFAULT; // 销售计量单位数量
	private String orderNum = DEFAULT; // 订单编号
	private String marketPrice = DEFAULT;// 市场价
	private String originPrice = DEFAULT; //原价
	private String price =DEFAULT;//基本计量单位单价
	private String mainPrice = DEFAULT; //销售计量单位单价

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getEmpInfo() {
		return empInfo;
	}

	public void setEmpInfo(String empInfo) {
		this.empInfo = empInfo;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getExpense() {
		return expense;
	}

	public void setExpense(String expense) {
		this.expense = expense;
	}

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getBankAcnt() {
		return bankAcnt;
	}

	public void setBankAcnt(String bankAcnt) {
		this.bankAcnt = bankAcnt;
	}

	public String getOrganizations() {
		return organizations;
	}

	public void setOrganizations(String organizations) {
		this.organizations = organizations;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getOperatorView() {
		return operatorView;
	}

	public void setOperatorView(String operatorView) {
		this.operatorView = operatorView;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public boolean isDebit() {
		return isDebit;
	}

	public void setDebit(boolean debit) {
		isDebit = debit;
	}

	public boolean isCredit() {
		return isCredit;
	}

	public void setCredit(boolean credit) {
		isCredit = credit;
	}

	public String getAccountBillNO() {
		return AccountBillNO;
	}

	public void setAccountBillNO(String accountBillNO) {
		AccountBillNO = accountBillNO;
	}

	public String getFDate() {
		return FDate;
	}

	public void setFDate(String fDate) {
		FDate = fDate;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getMainUnitName() {
		return mainUnitName;
	}

	public void setMainUnitName(String mainUnitName) {
		this.mainUnitName = mainUnitName;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getBeforeTexMoney() {
		return beforeTexMoney;
	}

	public void setBeforeTexMoney(String beforeTexMoney) {
		this.beforeTexMoney = beforeTexMoney;
	}

	public String getAfterTexMoney() {
		return afterTexMoney;
	}

	public void setAfterTexMoney(String afterTexMoney) {
		this.afterTexMoney = afterTexMoney;
	}

	public String getMainCount() {
		return mainCount;
	}

	public void setMainCount(String mainCount) {
		this.mainCount = mainCount;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getOriginPrice() {
		return originPrice;
	}

	public void setOriginPrice(String originPrice) {
		this.originPrice = originPrice;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getMainPrice() {
		return mainPrice;
	}

	public void setMainPrice(String mainPrice) {
		this.mainPrice = mainPrice;
	}
	
	

}
