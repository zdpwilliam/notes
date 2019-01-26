package com.sample.point;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.drools.RuleBase;
import org.drools.StatefulSession;
import org.drools.compiler.DroolsParserException;
import org.drools.compiler.PackageBuilder;
import org.drools.spi.Activation;

public class PointRuleEngineImpl implements PointRuleEngine {
	
	private RuleBase ruleBase;

	@Override
	public void initEngine() {
		// 设置时间格式
		System.setProperty("drools.dateformat", "yyyy-MM-dd HH:mm:ss");
		ruleBase = RuleBaseFacatory.getRuleBase();
		try {
			PackageBuilder backageBuilder = getPackageBuilderFromDrlFile();
			ruleBase.addPackages(backageBuilder.getPackages());
		} catch (DroolsParserException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void refreshEnginRule() {
		ruleBase = RuleBaseFacatory.getRuleBase();
		org.drools.rule.Package[] packages = ruleBase.getPackages();
		for (org.drools.rule.Package pg : packages) {
			ruleBase.removePackage(pg.getName());
		}
		initEngine();
	}

	@Override
	public void executeRuleEngine(final PointDomain pointDomain) {
		if (null == ruleBase.getPackages() || 0 == ruleBase.getPackages().length) {
			return;
		}
		StatefulSession statefulSession = ruleBase.newStatefulSession();
		statefulSession.insert(pointDomain);
		// fire
		statefulSession.fireAllRules(new org.drools.spi.AgendaFilter() {
			@Override
			public boolean accept(Activation activation) {
				return !activation.getRule().getName().contains("_test");
			}
		});
		statefulSession.dispose();
	}

	/**
	 * 从Drl规则文件中读取规则
	 * @return
	 * @throws IOException 
	 * @throws DroolsParserException 
	 */
	private PackageBuilder getPackageBuilderFromDrlFile() throws DroolsParserException, IOException {
		// 获取测试脚本文件
		List<String> drlFilePath = getTestDrlFile();
		// 装载测试脚本文件
		List<Reader> readers = readRuleFromDrlFile(drlFilePath);
		PackageBuilder backageBuilder = new PackageBuilder();
		for (Reader r : readers) {
			backageBuilder.addPackageFromDrl(r);
		}
		// 检查脚本是否有问题
		if (backageBuilder.hasErrors()) {
			throw new RuntimeException(backageBuilder.getErrors().toString());
		}
		return backageBuilder;
	}

	/**
	 * @param drlFilePath 脚本文件路径
	 * @return
	 * @throws FileNotFoundException
	 */
	private List<Reader> readRuleFromDrlFile(List<String> drlFilePath) throws FileNotFoundException {
		if (null == drlFilePath || 0 == drlFilePath.size()) {
			return null;
		}
		List<Reader> readers = new ArrayList<Reader>();
		for (String ruleFilePath : drlFilePath) {
			readers.add(new FileReader(new File(ruleFilePath)));
		}
		return readers;
	}

	/**
	 * 获取测试规则文件
	 * @return
	 */
	private List<String> getTestDrlFile() {
		List<String> drlFilePath = new ArrayList<String>();
		drlFilePath.add("/home/william/workspaces/eclipse-space/drools-demo/src/main/resources/addpoint.drl");
		drlFilePath.add("/home/william/workspaces/eclipse-space/drools-demo/src/main/resources/subpoint.drl");
		return drlFilePath;
	}

	public static void main(String[] args) throws IOException {
		PointRuleEngine pointRuleEngine = new PointRuleEngineImpl();
		while (true) {
			InputStream is = System.in;
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String input = br.readLine();
			System.out.println("请输入命令：");
			if (null != input && "s".equals(input)) {
				System.out.println("初始化规则引擎...");
				pointRuleEngine.initEngine();
				System.out.println("初始化规则引擎结束.");
			} else if ("e".equals(input)) {
				final PointDomain pointDomain = new PointDomain();
				System.out.println("初始化规则引擎...");
				pointRuleEngine.initEngine();
				System.out.println("初始化规则引擎结束.");
				pointDomain.setUserName("hello kity");
				pointDomain.setBackMondy(100d);
				pointDomain.setBuyMoney(500d);
				pointDomain.setBackNums(1);
				pointDomain.setBuyNums(5);
				pointDomain.setBillThisMonth(5);
				pointDomain.setBirthDay(true);
				pointDomain.setPoint(0L);
				pointRuleEngine.executeRuleEngine(pointDomain);
				System.out.println("执行完毕BillThisMonth：" + pointDomain.getBillThisMonth());
				System.out.println("执行完毕BuyMoney：" + pointDomain.getBuyMoney());
				System.out.println("执行完毕BuyNums：" + pointDomain.getBuyNums());
				System.out.println("执行完毕规则引擎决定发送积分：" + pointDomain.getPoint());
			} else if ("r".equals(input)) {
				System.out.println("刷新规则文件...");
				pointRuleEngine.refreshEnginRule();
				System.out.println("刷新规则文件结束.");
			}
		}
	}
}
