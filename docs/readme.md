atservice
 * zookeeper
 
 zk节点数：3
 ip：172.21.231.253  172.21.231.246  172.21.231.243
 
** zookeeper的搭建
### 上传
 上传用工具。
### 解压
 su – hadoop（切换到hadoop用户）
 tar -zxvf zookeeper-3.4.5.tar.gz（解压）
### 重命名
 mv zookeeper-3.4.5 zookeeper（重命名文件夹zookeeper-3.4.5为zookeeper）
### 修改环境变量
 #### 1、su – root(切换用户到root)
 #### 2、vi /etc/profile(修改文件)
 #### 3、添加内容：
 export ZOOKEEPER_HOME=/home/hadoop/zookeeperexport PATH=$PATH:$ZOOKEEPER_HOME/bin
 #### 4、重新编译文件：
 source /etc/profile
  ####5、注意：3台zookeeper都需要修改
  ####6、修改完成后切换回hadoop用户：
 su - hadoop
 ### 修改配置文件
  ####1、用hadoop用户操作
 cd zookeeper/conf
 cp zoo_sample.cfg zoo.cfg
  ####2、vi zoo.cfg
 #### 3、添加内容：
 dataDir=/home/hadoop/zookeeper/datadataLogDir=/home/hadoop/zookeeper/logserver.1=slave1:2888:3888 (主机名, 心跳端口、数据端口)server.2=slave2:2888:3888server.3=slave3:2888:3888
  ####4、创建文件夹：
 cd /home/hadoop/zookeeper/
 mkdir -m 755 data
 mkdir -m 755 log
  ####5、在data文件夹下新建myid文件，myid的文件内容为：
 cd data
 vi myid
 添加内容：
 1
  
  ### 将集群下发到其他机器上
 scp -r /home/hadoop/zookeeper hadoop@slave2:/home/hadoop/
 scp -r /home/hadoop/zookeeper hadoop@slave3:/home/hadoop/
  ### 修改其他机器的配置文件
 到slave2上：修改myid为：2
 到slave3上：修改myid为：3
  ### 启动（每台机器）
 zkServer.sh start
  ### 查看集群状态
  ####1、 jps（查看进程）
  ####2、 zkServer.sh status（查看集群状态，主从信息）
  
  单节点条件下可以使用docker，占用资源较少
  docker集群的搭建随后更新