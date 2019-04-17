package io.github.pleuvoir.master.testing.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * 命令行启动器<br>
 * 目前提供的命令：<br>
 * <ul>
 * 	<li>-s 或 --start，开启服务</li>
 * 	<li>-t 或 --stop，停止服务</li>
 * 	<li>-i xxx 或 --sign=xxx，执行xxx通道签到（只能在服务已启动后执行）</li>
 * 	<li>-j xxx 或 --job=xxx，执行xxx任务，若任务需要接收参数，增加参数-a或--args，多个参数以;分隔</li>
 *  <li>-e xxx 或 --encrypt=xxx，执行加密</li>
 * </ul>
 * @author abeir
 *
 */
public class Launcher {
	
	private static String help = "[-s/--start][-t/--stop][-i/--sign][-j/--job][-a/--args][-e/--encrypt] ";
	
	private static CommandLine cli; 
	
	private static Options opt = new Options();
	
	static{
		opt.addOption("s", "start", false, "Start Server");
		opt.addOption("t", "stop", false, "Stop Server");
		opt.addOption("i", "sign", true, "Institutions Sign In");
		
		opt.addOption("j", "job", true, "Jobs Running");
		opt.addOption("a", "args", true, "The job arguments");
		
		opt.addOption("e", "encrypt", true, "Encrypt Content");
	}
	
	private static CommandLine initCommandLine(String[] args){
		CommandLine cli = null;
		try {
			cli = new DefaultParser().parse(opt, args, true);
		} catch (ParseException e) {
			new HelpFormatter().printHelp(help, opt);
		}
		return cli;
	}
	
	public static void run(String cmd, String... value) throws Exception{
		switch (cmd) {
		case "start":
			System.out.println("ServerRunner.create().start();");
			break;
		case "stop":
			System.out.println("ServerRunner.create().stop();");
			break;
		case "sign":
		System.out.println("ChannelSignOn.create().exec(value[0]);");
			break;
		case "job":
			if(cli.hasOption("a")){  //有参数  -j helloJob -a 1;2;3
				//TimerScheduler.exec(value[0], cli.getOptionValue("args"));
				System.out.println("TimerScheduler.exec(value[0], cli.getOptionValue(\"args\"));");
				System.out.println("任务参数已;分割==" + cli.getOptionValue("args"));
			}else{ //无参数 -j helloJob
				System.out.println("TimerScheduler.exec(value[0], null);");
			}
			break;
		case "encrypt":
			System.out.println("EncryptRunner.create().encode(value[0]);");
			break;
		default:
			System.out.println("未输入有效的指令");
			break;
		}
	}
	
	
	public static void main(String[] args) {
		cli = initCommandLine(args);
		
		try {
			if(cli.hasOption("s")){
				run("start");
			}else if(cli.hasOption("t")){
				run("stop");
			}else if(cli.hasOption("i")){
				run("sign", cli.getOptionValue("sign"));
			}else if(cli.hasOption("j")){
				run("job", cli.getOptionValues("job"));
			}else if(cli.hasOption("e")){
				run("encrypt", cli.getOptionValues("encrypt"));
			}else{
				new HelpFormatter().printHelp(help, opt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
