# atshop
基于springcloud的商城类网站

## 2018-01-25
初始化应用工程，添加注册中心，工作流模块，用户管理模块，

项目规划：
* 后台管理： ng-admin
* 单点登录
* 站内搜索
* 电影信息网站
* 商品详情
* 用户管理

部署：
dockerfile 

JMS:
activeMQ

打包后放置于应用文件夹，准备dockerfile。具体实现方式，请使用脚本，推荐groovy或者python

使用的docker镜像：
1. dockerhub：
2. 本地应用镜像：

配置：
* mysql
> docker run --name mysql -p 12345:3306 -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql:5.6.35
* redis

# 前端部分
### 后台管理
后台管理采用ng-admin2(ngx-admin),修改部分功能。

# 数据抓取
数据源：


import java.util.regex.Pattern;
public class App {
    private static Pattern pattern;
    private static Pattern ptipv4;
    static {
        // ipv6
        pattern = Pattern.compile("^((([0-9A-Fa-f]{1,4}:){7}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){1,7}:)|(([0-9A-Fa-f]{1,4}:){6}:[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){5}(:[0-9A-Fa-f]{1,4}){1,2})|(([0-9A-Fa-f]{1,4}:){4}(:[0-9A-Fa-f]{1,4}){1,3})|(([0-9A-Fa-f]{1,4}:){3}(:[0-9A-Fa-f]{1,4}){1,4})|(([0-9A-Fa-f]{1,4}:){2}(:[0-9A-Fa-f]{1,4}){1,5})|([0-9A-Fa-f]{1,4}:(:[0-9A-Fa-f]{1,4}){1,6})|(:(:[0-9A-Fa-f]{1,4}){1,7})|(([0-9A-Fa-f]{1,4}:){6}(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3})|(([0-9A-Fa-f]{1,4}:){5}:(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3})|(([0-9A-Fa-f]{1,4}:){4}(:[0-9A-Fa-f]{1,4}){0,1}:(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3})|(([0-9A-Fa-f]{1,4}:){3}(:[0-9A-Fa-f]{1,4}){0,2}:(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3})|(([0-9A-Fa-f]{1,4}:){2}(:[0-9A-Fa-f]{1,4}){0,3}:(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3})|([0-9A-Fa-f]{1,4}:(:[0-9A-Fa-f]{1,4}){0,4}:(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3})|(:(:[0-9A-Fa-f]{1,4}){0,5}:(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}))$");
        // ipv4
        ptipv4 = Pattern.compile("^(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}$");
    }
    public static void main( String[] args ) {
        System.out.println(pattern.matcher("2001:0DB8:0000:0023:0008:0800:200C:417A").matches());
        System.out.println(pattern.matcher("2001:DB8:0:23:8:800:200C:417A").matches());
        System.out.println(pattern.matcher("2001:DB8:0:23:8:800:192.1.0.0").matches());
        System.out.println(pattern.matcher("2001:DB8:0::800:192.1.0.0").matches());
        System.out.println(pattern.matcher("2001:DB8:0:23::192.1.0.0").matches());
        System.out.println(pattern.matcher("::192.1.0.0").matches());
        System.out.println(pattern.matcher("1:af::3").matches());
        System.out.println(pattern.matcher("1:af::").matches());
        System.out.println(pattern.matcher("::1:af:0").matches());
        System.out.println(pattern.matcher("::0").matches());
        System.out.println("---------------------");
        System.out.println(pattern.matcher("+2001:0DB8:0000:0023:0008:0800:200C:417A").matches());
        System.out.println(pattern.matcher("2001:0DB8:0z00:0023:0008:0800:200C:417A").matches());
        System.out.println(pattern.matcher("2001:DB8:0:23:800::192.1.0.0.1").matches());
        System.out.println(pattern.matcher("2001:DB8::23::800:192.1.0.0").matches());
        System.out.println(pattern.matcher(":::").matches());
        System.out.println(pattern.matcher("1:::2").matches());

        System.out.println("---------------------");
        System.out.println(ptipv4.matcher("1.1.0.1").matches());
        System.out.println(ptipv4.matcher("123.1.0.19").matches());
        System.out.println(ptipv4.matcher("255.255.255.255").matches());
        System.out.println(ptipv4.matcher("0.0.0.0").matches());
        System.out.println("---------------------");
        System.out.println(ptipv4.matcher("-1.1.0.1").matches());
        System.out.println(ptipv4.matcher("1.1b.0.1").matches());
        System.out.println(ptipv4.matcher("1.01.0.1").matches());
        System.out.println(ptipv4.matcher("1.1.300.1").matches());
        System.out.println(ptipv4.matcher("1.1..1").matches());
    }
}
