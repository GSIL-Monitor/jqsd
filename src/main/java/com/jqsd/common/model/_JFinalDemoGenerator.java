package com.jqsd.common.model;

import javax.sql.DataSource;

import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.dialect.SqlServerDialect;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jqsd.common.AppConfig;

/**
 * 本 demo 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * 
 * 在数据库表有任何变动时，运行一下 main 方法，极速响应变化进行代码重构
 */
public class _JFinalDemoGenerator {
	
	public static DataSource getDataSource() {
		PropKit.use("app-config.txt");
		DruidPlugin druidPlugin = AppConfig.createDruidPlugin();
		druidPlugin.start();
		return druidPlugin.getDataSource();
	}
	
	public static void main(String[] args) {
		// base model 所使用的包名
		String baseModelPackageName = "com.jqsd.common.model.base";
		// base model 文件保存路径
		String baseModelOutputDir = PathKit.getWebRootPath() + "/src/main/java/com/jqsd/common/model/base";
		
		// model 所使用的包名 (MappingKit 默认使用的包名)
		String modelPackageName = "com.jqsd.common.model";
		// model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
		String modelOutputDir = baseModelOutputDir + "/..";
		
		// 创建生成器
		Generator generator = new Generator(getDataSource(), baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
		//Generator generator = new Generator(getDataSource(), baseModelPackageName, baseModelOutputDir);

		// 设置是否生成链式 setter 方法
		generator.setGenerateChainSetter(false);

		//数据库 方言
		generator.setDialect(new SqlServerDialect());

		// 添加不需要生成的表名
		generator.addExcludedTable(getExcludedTable());


		// 设置是否在 Model 中生成 dao 对象
		generator.setGenerateDaoInModel(true);
		// 设置是否生成链式 setter 方法
		generator.setGenerateChainSetter(true);
		// 设置是否生成字典文件
		generator.setGenerateDataDictionary(false);
		// 设置需要被移除的表名前缀用于生成modelName。例如表名 "osc_user"，移除前缀 "osc_"后生成的model名为 "User"而非 OscUser
		generator.setRemovedTableNamePrefixes("t_yf_");
		// 生成
		generator.generate();

	}

	// 添加不需要生成的表名
	private static String[] getExcludedTable(){
		String[] table = {"F_AccountHead",
							"H_OrderHead",
							"C_CommInsureHead_Customer_pay",
							"C_CommInsureHead_business_pay",
							"C_CommInsureHead_Cost",
							"C_Host",
							"C_ProcessHead",
							"DX_DepartmentName",
							"H_ExtWty",
							"C_StorageBillNo",
							"C_ServiceFormulaSale",
							"H_SHBuyBill",
							"H_SHRentBill",
							"H_SHSaleBill",
							"H_Inbody",
							"t_outhead",
							"DX_BankName",
							"DX_K3ACCOUNT_BANK",
							"DX_K3ACCOUNT",
		};


		return table;
	}
}




