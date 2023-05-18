/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.7.19-log : Database - reggie
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`reggie` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `reggie`;

/*Table structure for table `address_book` */

DROP TABLE IF EXISTS `address_book`;

CREATE TABLE `address_book` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `consignee` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '收货人',
  `sex` tinyint(4) NOT NULL COMMENT '性别 0 女 1 男',
  `phone` varchar(11) COLLATE utf8_bin NOT NULL COMMENT '手机号',
  `province_code` varchar(12) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '省级区划编号',
  `province_name` varchar(32) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '省级名称',
  `city_code` varchar(12) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '市级区划编号',
  `city_name` varchar(32) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '市级名称',
  `district_code` varchar(12) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '区级区划编号',
  `district_name` varchar(32) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '区级名称',
  `detail` varchar(200) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '详细地址',
  `label` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '标签',
  `is_default` tinyint(1) NOT NULL DEFAULT '0' COMMENT '默认 0 否 1是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `update_user` bigint(20) NOT NULL COMMENT '修改人',
  `is_deleted` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='地址管理';

/*Data for the table `address_book` */

insert  into `address_book`(`id`,`user_id`,`consignee`,`sex`,`phone`,`province_code`,`province_name`,`city_code`,`city_name`,`district_code`,`district_name`,`detail`,`label`,`is_default`,`create_time`,`update_time`,`create_user`,`update_user`,`is_deleted`) values 
(1417414526093082626,1417012167126876162,'小明',1,'13812345678',NULL,NULL,NULL,NULL,NULL,NULL,'昌平区金燕龙办公楼','公司',1,'2021-07-20 17:22:12','2021-07-20 17:26:33',1417012167126876162,1417012167126876162,0),
(1417414926166769666,1417012167126876162,'小李',1,'13512345678',NULL,NULL,NULL,NULL,NULL,NULL,'测试','家',0,'2021-07-20 17:23:47','2021-07-20 17:23:47',1417012167126876162,1417012167126876162,0),
(1649230837804388354,1648944780710813698,'小白',1,'15215454758',NULL,NULL,NULL,NULL,NULL,NULL,'1111','公司',0,'2023-04-21 09:57:28','2023-04-28 08:58:31',1648944780710813698,1648944780710813698,0),
(1649234017854361602,1648944780710813698,'王先生',1,'15216524358',NULL,NULL,NULL,NULL,NULL,NULL,'北京市','公司',1,'2023-04-21 10:10:06','2023-04-28 10:46:56',1648944780710813698,1648944780710813698,0);

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `type` int(11) DEFAULT NULL COMMENT '类型   1 菜品分类 2 套餐分类',
  `name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '分类名称',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '顺序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `update_user` bigint(20) NOT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_category_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜品及套餐分类';

/*Data for the table `category` */

insert  into `category`(`id`,`type`,`name`,`sort`,`create_time`,`update_time`,`create_user`,`update_user`) values 
(1397844263642378242,1,'湘菜',17,'2021-05-27 09:16:58','2023-04-04 11:12:14',1,1),
(1397844303408574465,1,'川菜',2,'2021-05-27 09:17:07','2021-06-02 14:27:22',1,1),
(1397844391040167938,1,'粤菜',3,'2021-05-27 09:17:28','2021-07-09 14:37:13',1,1),
(1413341197421846529,1,'饮品',11,'2021-07-09 11:36:15','2021-07-09 14:39:15',1,1),
(1413384954989060097,1,'主食',12,'2021-07-09 14:30:07','2021-07-09 14:39:19',1,1),
(1413386191767674881,2,'儿童套餐',6,'2021-07-09 14:35:02','2021-07-09 14:39:05',1,1),
(1644141694436286465,2,'美味套餐',23,'2023-04-07 08:55:01','2023-04-24 19:24:18',1,1);

/*Table structure for table `dish` */

DROP TABLE IF EXISTS `dish`;

CREATE TABLE `dish` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '菜品名称',
  `category_id` bigint(20) NOT NULL COMMENT '菜品分类id',
  `price` decimal(10,2) DEFAULT NULL COMMENT '菜品价格',
  `code` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '商品码',
  `image` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '图片',
  `description` varchar(400) COLLATE utf8_bin DEFAULT NULL COMMENT '描述信息',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '0 停售 1 起售',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '顺序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `update_user` bigint(20) NOT NULL COMMENT '修改人',
  `is_deleted` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_dish_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜品管理';

/*Data for the table `dish` */

insert  into `dish`(`id`,`name`,`category_id`,`price`,`code`,`image`,`description`,`status`,`sort`,`create_time`,`update_time`,`create_user`,`update_user`,`is_deleted`) values 
(1397849739276890114,'辣子鸡',1397844263642378242,7800.00,'222222222','cfac6e36-23b0-4b1a-a800-ecc2240b132c.jpg','来自鲜嫩美味的小鸡，值得一尝',1,0,'2021-05-27 09:38:43','2023-04-21 11:18:50',1,1648944780710813698,0),
(1397850140982161409,'毛氏红烧肉',1397844263642378242,6800.00,'123412341234','4d548001-b7ba-4491-bbe0-257bec6e1b33.jpeg','毛氏红烧肉毛氏红烧肉，确定不来一份？',0,0,'2021-05-27 09:40:19','2023-05-18 09:45:22',1,1648944780710813698,0),
(1397850392090947585,'组庵鱼翅',1397844263642378242,4800.00,'123412341234','f0c6566c-c3a9-4481-ac09-0563a5a87d95.jpg','组庵鱼翅，看图足以表明好吃程度',1,0,'2021-05-27 09:41:19','2023-04-21 11:19:00',1,1648944780710813698,0),
(1397850851245600769,'霸王别姬',1397844263642378242,12800.00,'123412341234','d294b49e-bbfc-4541-b28e-8e6c207a6531.jpg','还有什么比霸王别姬更美味的呢？',1,0,'2021-05-27 09:43:08','2023-04-21 11:19:06',1,1648944780710813698,0),
(1644138082201026561,'大包子',1413384954989060097,200.00,'','6941d87d-9f77-457d-affe-cff55c5c889b.jpg','强强强强',1,0,'2023-04-07 08:40:40','2023-04-21 11:19:27',1,1648944780710813698,0),
(1644139533027549185,'包子',1413384954989060097,400.00,'','29657d1c-0b9e-4ac9-8e2f-a41dc2648a19.jpg','大包子，好好吃',1,0,'2023-04-07 08:46:26','2023-04-24 09:28:54',1,1648944780710813698,0),
(1650310966652452865,'电脑',1397844303408574465,11200.00,'','8aa70953-d441-4d34-8a25-35d19fc7c86d.jpg','电脑真好吃',1,0,'2023-04-24 09:29:30','2023-04-24 09:29:30',1648944780710813698,1648944780710813698,0),
(1650460758330339329,'delicious',1413384954989060097,12100.00,'','66c59aba-c184-49da-8c47-eca30e18e559.jpg','',1,0,'2023-04-24 19:24:44','2023-05-18 09:46:12',1,1648944780710813698,0);

/*Table structure for table `dish_flavor` */

DROP TABLE IF EXISTS `dish_flavor`;

CREATE TABLE `dish_flavor` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dish_id` bigint(20) NOT NULL COMMENT '菜品',
  `name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '口味名称',
  `value` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '口味数据list',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `update_user` bigint(20) NOT NULL COMMENT '修改人',
  `is_deleted` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜品口味关系表';

/*Data for the table `dish_flavor` */

insert  into `dish_flavor`(`id`,`dish_id`,`name`,`value`,`create_time`,`update_time`,`create_user`,`update_user`,`is_deleted`) values 
(1397849417888346113,1397849417854791681,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 09:37:27','2021-05-27 09:37:27',1,1,0),
(1397849739297861633,1397849739276890114,'忌口','[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]','2023-04-21 11:18:50','2023-04-21 11:18:50',1648944780710813698,1648944780710813698,0),
(1397849739323027458,1397849739276890114,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2023-04-21 11:18:50','2023-04-21 11:18:50',1648944780710813698,1648944780710813698,0),
(1397849936421761025,1397849936404983809,'忌口','[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]','2021-05-27 09:39:30','2021-05-27 09:39:30',1,1,0),
(1397849936438538241,1397849936404983809,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 09:39:30','2021-05-27 09:39:30',1,1,0),
(1397850141015715841,1397850140982161409,'忌口','[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]','2023-04-21 11:19:21','2023-04-21 11:19:21',1648944780710813698,1648944780710813698,0),
(1397850141040881665,1397850140982161409,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2023-04-21 11:19:21','2023-04-21 11:19:21',1648944780710813698,1648944780710813698,0),
(1397850392120307713,1397850392090947585,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2023-04-21 11:19:00','2023-04-21 11:19:00',1648944780710813698,1648944780710813698,0),
(1397850392137084929,1397850392090947585,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2023-04-21 11:19:00','2023-04-21 11:19:00',1648944780710813698,1648944780710813698,0),
(1397850630734262274,1397850630700707841,'忌口','[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]','2021-05-27 09:42:16','2021-05-27 09:42:16',1,1,0),
(1397850630755233794,1397850630700707841,'辣度','[\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 09:42:16','2021-05-27 09:42:16',1,1,0),
(1397850851274960898,1397850851245600769,'忌口','[\"不要蒜\",\"不要香菜\",\"不要辣\"]','2023-04-21 11:19:06','2023-04-21 11:19:06',1648944780710813698,1648944780710813698,0),
(1397850851283349505,1397850851245600769,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2023-04-21 11:19:06','2023-04-21 11:19:06',1648944780710813698,1648944780710813698,0),
(1397851099523231745,1397851099502260226,'忌口','[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]','2021-05-27 09:44:08','2021-05-27 09:44:08',1,1,0),
(1397851099527426050,1397851099502260226,'辣度','[\"不辣\",\"微辣\",\"中辣\"]','2021-05-27 09:44:08','2021-05-27 09:44:08',1,1,0),
(1397851370483658754,1397851370462687234,'温度','[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]','2021-05-27 09:45:12','2021-05-27 09:45:12',1,1,0),
(1397851370483658755,1397851370462687234,'忌口','[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]','2021-05-27 09:45:12','2021-05-27 09:45:12',1,1,0),
(1397851370483658756,1397851370462687234,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 09:45:12','2021-05-27 09:45:12',1,1,0),
(1397851668283437058,1397851668262465537,'温度','[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]','2021-05-27 09:46:23','2021-05-27 09:46:23',1,1,0),
(1397852391180120065,1397852391150759938,'忌口','[\"不要葱\",\"不要香菜\",\"不要辣\"]','2021-05-27 09:49:16','2021-05-27 09:49:16',1,1,0),
(1397852391196897281,1397852391150759938,'辣度','[\"不辣\",\"微辣\",\"重辣\"]','2021-05-27 09:49:16','2021-05-27 09:49:16',1,1,0),
(1397853183307984898,1397853183287013378,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 09:52:24','2021-05-27 09:52:24',1,1,0),
(1397853423486414850,1397853423461249026,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 09:53:22','2021-05-27 09:53:22',1,1,0),
(1397853709126905857,1397853709101740034,'忌口','[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]','2021-05-27 09:54:30','2021-05-27 09:54:30',1,1,0),
(1397853890283089922,1397853890262118402,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 09:55:13','2021-05-27 09:55:13',1,1,0),
(1397854133632413697,1397854133603053569,'温度','[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]','2021-05-27 09:56:11','2021-05-27 09:56:11',1,1,0),
(1397854652623007745,1397854652581064706,'忌口','[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]','2021-05-27 09:58:15','2021-05-27 09:58:15',1,1,0),
(1397854652635590658,1397854652581064706,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 09:58:15','2021-05-27 09:58:15',1,1,0),
(1397854865735593986,1397854865672679425,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 09:59:06','2021-05-27 09:59:06',1,1,0),
(1397855742303186946,1397855742273826817,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 10:02:35','2021-05-27 10:02:35',1,1,0),
(1397855906497605633,1397855906468245506,'忌口','[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]','2021-05-27 10:03:14','2021-05-27 10:03:14',1,1,0),
(1397856190573621250,1397856190540066818,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 10:04:21','2021-05-27 10:04:21',1,1,0),
(1397859056709316609,1397859056684150785,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 10:15:45','2021-05-27 10:15:45',1,1,0),
(1397859277837217794,1397859277812051969,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 10:16:37','2021-05-27 10:16:37',1,1,0),
(1397859487502086146,1397859487476920321,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 10:17:27','2021-05-27 10:17:27',1,1,0),
(1397859757061615618,1397859757036449794,'甜味','[\"无糖\",\"少糖\",\"半躺\",\"多糖\",\"全糖\"]','2021-05-27 10:18:32','2021-05-27 10:18:32',1,1,0),
(1397860242086735874,1397860242057375745,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 10:20:27','2021-05-27 10:20:27',1,1,0),
(1397860963918065665,1397860963880316929,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 10:23:19','2021-05-27 10:23:19',1,1,0),
(1397861135754506242,1397861135733534722,'甜味','[\"无糖\",\"少糖\",\"半躺\",\"多糖\",\"全糖\"]','2021-05-27 10:24:00','2021-05-27 10:24:00',1,1,0),
(1397861370035744769,1397861370010578945,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 10:24:56','2021-05-27 10:24:56',1,1,0),
(1397861683459305474,1397861683434139649,'忌口','[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]','2021-05-27 10:26:11','2021-05-27 10:26:11',1,1,0),
(1397861898467717121,1397861898438356993,'忌口','[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]','2021-05-27 10:27:02','2021-05-27 10:27:02',1,1,0),
(1397862198054268929,1397862198033297410,'忌口','[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]','2021-05-27 10:28:14','2021-05-27 10:28:14',1,1,0),
(1397862477835317250,1397862477831122945,'辣度','[\"不辣\",\"微辣\",\"中辣\"]','2021-05-27 10:29:20','2021-05-27 10:29:20',1,1,0),
(1398089545865015297,1398089545676271617,'温度','[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]','2021-05-28 01:31:38','2021-05-28 01:31:38',1,1,0),
(1398089782323097601,1398089782285348866,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-28 01:32:34','2021-05-28 01:32:34',1,1,0),
(1398090003262255106,1398090003228700673,'忌口','[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]','2021-05-28 01:33:27','2021-05-28 01:33:27',1,1,0),
(1398090264554811394,1398090264517062657,'忌口','[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]','2021-05-28 01:34:29','2021-05-28 01:34:29',1,1,0),
(1398090455399837698,1398090455324340225,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-28 01:35:14','2021-05-28 01:35:14',1,1,0),
(1398090685449023490,1398090685419663362,'温度','[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]','2021-05-28 01:36:09','2021-05-28 01:36:09',1,1,0),
(1398090825358422017,1398090825329061889,'忌口','[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]','2021-05-28 01:36:43','2021-05-28 01:36:43',1,1,0),
(1398091007051476993,1398091007017922561,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-28 01:37:26','2021-05-28 01:37:26',1,1,0),
(1398091296164851713,1398091296131297281,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-28 01:38:35','2021-05-28 01:38:35',1,1,0),
(1398091546531246081,1398091546480914433,'忌口','[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]','2021-05-28 01:39:35','2021-05-28 01:39:35',1,1,0),
(1398091729809747969,1398091729788776450,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-28 01:40:18','2021-05-28 01:40:18',1,1,0),
(1398091889499484161,1398091889449152513,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-28 01:40:56','2021-05-28 01:40:56',1,1,0),
(1398092095179763713,1398092095142014978,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-28 01:41:45','2021-05-28 01:41:45',1,1,0),
(1398092283877306370,1398092283847946241,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-28 01:42:30','2021-05-28 01:42:30',1,1,0),
(1398094018939236354,1398094018893099009,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-28 01:49:24','2021-05-28 01:49:24',1,1,0),
(1398094391494094850,1398094391456346113,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-28 01:50:53','2021-05-28 01:50:53',1,1,0),
(1399574026165727233,1399305325713600514,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-06-01 03:50:25','2021-06-01 03:50:25',1399309715396669441,1399309715396669441,0),
(1413389540592263169,1413384757047271425,'温度','[\"常温\",\"冷藏\"]','2021-07-12 09:09:16','2021-07-12 09:09:16',1,1,0),
(1413389684020682754,1413342036832100354,'温度','[\"常温\",\"冷藏\"]','2021-07-09 15:12:18','2021-07-09 15:12:18',1,1,0),
(1643268314719395843,1643268314719395842,'甜味','[\"无糖\",\"少糖\",\"半糖\",\"多糖\",\"全糖\"]','2023-04-04 23:04:31','2023-04-04 23:04:31',1,1,0),
(1643268314794893314,1643268314719395842,'温度','[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]','2023-04-04 23:04:31','2023-04-04 23:04:31',1,1,0),
(1643268472899182594,1643268472899182593,'甜味','[\"无糖\",\"少糖\",\"半糖\",\"多糖\",\"全糖\"]','2023-04-04 23:05:09','2023-04-04 23:05:09',1,1,0),
(1643483630350663682,1643268834863423489,'忌口','[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]','2023-04-05 13:20:07','2023-04-05 13:20:07',1,1,0),
(1643483630350663683,1643268834863423489,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2023-04-05 13:20:07','2023-04-05 13:20:07',1,1,0),
(1644138082221998081,1644138082201026561,'甜味','[\"无糖\",\"少糖\",\"半糖\",\"多糖\",\"全糖\"]','2023-04-21 11:19:27','2023-04-21 11:19:27',1648944780710813698,1648944780710813698,0);

/*Table structure for table `employee` */

DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '姓名',
  `username` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `password` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `phone` varchar(11) COLLATE utf8_bin NOT NULL COMMENT '手机号',
  `sex` varchar(2) COLLATE utf8_bin NOT NULL COMMENT '性别',
  `id_number` varchar(18) COLLATE utf8_bin NOT NULL COMMENT '身份证号',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态 0:禁用，1:正常',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `update_user` bigint(20) NOT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='员工信息';

/*Data for the table `employee` */

insert  into `employee`(`id`,`name`,`username`,`password`,`phone`,`sex`,`id_number`,`status`,`create_time`,`update_time`,`create_user`,`update_user`) values 
(1,'管理员','admin','e10adc3949ba59abbe56e057f20f883e','13812312312','1','110101199001010047',1,'2021-05-06 17:20:07','2023-04-03 16:31:25',1,1),
(1641336015001489410,'李文杰','SuperStar','e10adc3949ba59abbe56e057f20f883e','15216853638','1','111122223333444455',1,'2023-03-30 15:06:15','2023-04-03 16:39:09',1,1),
(1641336452198961154,'Li文杰','SuperStar1','e10adc3949ba59abbe56e057f20f883e','15212312312','1','121212121212121212',1,'2023-03-30 15:07:59','2023-04-03 16:26:28',1,1),
(1642811248900354050,'数据分析','12121212','e10adc3949ba59abbe56e057f20f883e','15945645685','1','121212121212121212',1,'2023-04-03 16:48:27','2023-04-03 16:48:28',1,1),
(1643071505208238081,'何宇恒','sssbbbb1','e10adc3949ba59abbe56e057f20f883e','15215845454','1','121212145214563256',1,'2023-04-04 10:02:28','2023-04-24 19:24:08',1,1),
(1658719577329664002,'李文及','15216853629','e10adc3949ba59abbe56e057f20f883e','15216553638','1','410222200211229852',1,'2023-05-17 14:22:19','2023-05-17 14:22:19',1,1);

/*Table structure for table `order_detail` */

DROP TABLE IF EXISTS `order_detail`;

CREATE TABLE `order_detail` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '名字',
  `image` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '图片',
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `dish_id` bigint(20) DEFAULT NULL COMMENT '菜品id',
  `setmeal_id` bigint(20) DEFAULT NULL COMMENT '套餐id',
  `dish_flavor` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '口味',
  `number` int(11) NOT NULL DEFAULT '1' COMMENT '数量',
  `amount` decimal(10,2) NOT NULL COMMENT '金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='订单明细表';

/*Data for the table `order_detail` */

insert  into `order_detail`(`id`,`name`,`image`,`order_id`,`dish_id`,`setmeal_id`,`dish_flavor`,`number`,`amount`) values 
(1651752674950451202,'电脑','8aa70953-d441-4d34-8a25-35d19fc7c86d.jpg',1651778025458036737,1650310966652452865,NULL,NULL,2,112.00),
(1651753282189201409,'123','b5705d11-78fa-4bf5-8761-758807b51ef5.jpg',1651778025458036737,NULL,1650461066041257985,NULL,7,12.00),
(1651753305534697473,'毛氏红烧肉','4d548001-b7ba-4491-bbe0-257bec6e1b33.jpeg',1651778025458036737,1397850140982161409,NULL,'不要香菜,重辣',1,68.00),
(1651778227854139394,'123','b5705d11-78fa-4bf5-8761-758807b51ef5.jpg',1651778290198274050,NULL,1650461066041257985,NULL,2,12.00),
(1651778245474410498,'毛氏红烧肉','4d548001-b7ba-4491-bbe0-257bec6e1b33.jpeg',1651778290198274050,1397850140982161409,NULL,'不要香菜,重辣',4,68.00),
(1651780082256629761,'123','b5705d11-78fa-4bf5-8761-758807b51ef5.jpg',1651780113906847745,NULL,1650461066041257985,NULL,6,12.00);

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `number` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '订单号',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '订单状态 1待付款，2待派送，3已派送，4已完成，5已取消',
  `user_id` bigint(20) NOT NULL COMMENT '下单用户',
  `address_book_id` bigint(20) NOT NULL COMMENT '地址id',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `checkout_time` datetime DEFAULT NULL COMMENT '结账时间',
  `pay_method` int(11) NOT NULL DEFAULT '1' COMMENT '支付方式 1微信,2支付宝',
  `amount` decimal(10,2) NOT NULL COMMENT '实收金额',
  `remark` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `phone` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `address` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `user_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `consignee` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='订单表';

/*Data for the table `orders` */

insert  into `orders`(`id`,`number`,`status`,`user_id`,`address_book_id`,`order_time`,`checkout_time`,`pay_method`,`amount`,`remark`,`phone`,`address`,`user_name`,`consignee`) values 
(1651778025458036737,'1651778009016365057',1,1648944780710813698,1649230837804388354,'2023-04-28 10:39:02',NULL,1,376.00,'','15215454758','1111',NULL,NULL),
(1651778290198274050,'1651778290198274049',1,1648944780710813698,1649234017854361602,'2023-04-28 10:40:08',NULL,1,296.00,'','15216524358','22212312',NULL,NULL),
(1651780113906847745,'1651780113839738882',1,1648944780710813698,1649234017854361602,'2023-04-28 10:47:22',NULL,1,72.00,'哈哈哈哈哈','15216524358','北京市',NULL,NULL);

/*Table structure for table `setmeal` */

DROP TABLE IF EXISTS `setmeal`;

CREATE TABLE `setmeal` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `category_id` bigint(20) NOT NULL COMMENT '菜品分类id',
  `name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '套餐名称',
  `price` decimal(10,2) NOT NULL COMMENT '套餐价格',
  `status` int(11) DEFAULT NULL COMMENT '状态 0:停用 1:启用',
  `code` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '编码',
  `description` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '描述信息',
  `image` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '图片',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `update_user` bigint(20) NOT NULL COMMENT '修改人',
  `is_deleted` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_setmeal_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='套餐';

/*Data for the table `setmeal` */

insert  into `setmeal`(`id`,`category_id`,`name`,`price`,`status`,`code`,`description`,`image`,`create_time`,`update_time`,`create_user`,`update_user`,`is_deleted`) values 
(1644175926768558082,1413386191767674881,'qq',12300.00,1,'','124出题人43如此','46118ba6-2176-43ef-ac9b-fdebe103a1ba.jpg','2023-04-07 11:11:03','2023-05-18 09:19:38',1,1648944780710813698,0),
(1650461066041257985,1644141694436286465,'123',1200.00,1,'','','b5705d11-78fa-4bf5-8761-758807b51ef5.jpg','2023-04-24 19:25:57','2023-05-18 09:53:45',1,1648944780710813698,0);

/*Table structure for table `setmeal_dish` */

DROP TABLE IF EXISTS `setmeal_dish`;

CREATE TABLE `setmeal_dish` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `setmeal_id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '套餐id ',
  `dish_id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '菜品id',
  `name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '菜品名称 （冗余字段）',
  `price` decimal(10,2) DEFAULT NULL COMMENT '菜品原价（冗余字段）',
  `copies` int(11) NOT NULL COMMENT '份数',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `update_user` bigint(20) NOT NULL COMMENT '修改人',
  `is_deleted` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='套餐菜品关系';

/*Data for the table `setmeal_dish` */

insert  into `setmeal_dish`(`id`,`setmeal_id`,`dish_id`,`name`,`price`,`copies`,`sort`,`create_time`,`update_time`,`create_user`,`update_user`,`is_deleted`) values 
(1650460814668230658,'1644175926768558082','1397850392090947585','组庵鱼翅',4800.00,1,0,'2023-04-24 19:24:57','2023-04-24 19:24:57',1,1,0),
(1650460814668230659,'1644175926768558082','1397850851245600769','霸王别姬',12800.00,1,0,'2023-04-24 19:24:57','2023-04-24 19:24:57',1,1,0),
(1650461066108366849,'1650461066041257985','1397850140982161409','毛氏红烧肉',6800.00,1,0,'2023-04-24 19:25:57','2023-04-24 19:25:57',1,1,0),
(1650461066108366850,'1650461066041257985','1397850851245600769','霸王别姬',12800.00,1,0,'2023-04-24 19:25:57','2023-04-24 19:25:57',1,1,0);

/*Table structure for table `shopping_cart` */

DROP TABLE IF EXISTS `shopping_cart`;

CREATE TABLE `shopping_cart` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `image` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '图片',
  `user_id` bigint(20) NOT NULL COMMENT '主键',
  `dish_id` bigint(20) DEFAULT NULL COMMENT '菜品id',
  `setmeal_id` bigint(20) DEFAULT NULL COMMENT '套餐id',
  `dish_flavor` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '口味',
  `number` int(11) NOT NULL DEFAULT '1' COMMENT '数量',
  `amount` decimal(10,2) NOT NULL COMMENT '金额',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='购物车';

/*Data for the table `shopping_cart` */

insert  into `shopping_cart`(`id`,`name`,`image`,`user_id`,`dish_id`,`setmeal_id`,`dish_flavor`,`number`,`amount`,`create_time`) values 
(1650340280685080578,'霸王别姬','d294b49e-bbfc-4541-b28e-8e6c207a6531.jpg',1650340127181942785,1397850851245600769,NULL,'不要辣,中辣',1,128.00,NULL),
(1650344795060592642,'毛氏红烧肉','4d548001-b7ba-4491-bbe0-257bec6e1b33.jpeg',1650344694837698561,1397850140982161409,NULL,'不要香菜,重辣',1,68.00,NULL),
(1650345598039113730,'包子','29657d1c-0b9e-4ac9-8e2f-a41dc2648a19.jpg',1650345553688543234,1644139533027549185,NULL,NULL,1,4.00,NULL),
(1650345615210594306,'包子','29657d1c-0b9e-4ac9-8e2f-a41dc2648a19.jpg',1650345553688543234,1644139533027549185,NULL,NULL,1,4.00,NULL),
(1650345642570039298,'包子','29657d1c-0b9e-4ac9-8e2f-a41dc2648a19.jpg',1650345553688543234,1644139533027549185,NULL,NULL,1,4.00,NULL),
(1650345646625931265,'包子','29657d1c-0b9e-4ac9-8e2f-a41dc2648a19.jpg',1650345553688543234,1644139533027549185,NULL,NULL,1,4.00,NULL),
(1650345678649442305,'包子','29657d1c-0b9e-4ac9-8e2f-a41dc2648a19.jpg',1650345553688543234,1644139533027549185,NULL,NULL,1,4.00,NULL),
(1650455853607694337,'大包子','6941d87d-9f77-457d-affe-cff55c5c889b.jpg',1650455302660698114,1644138082201026561,NULL,'半糖',1,2.00,NULL),
(1650455870351355905,'包子','29657d1c-0b9e-4ac9-8e2f-a41dc2648a19.jpg',1650455302660698114,1644139533027549185,NULL,NULL,1,4.00,NULL),
(1650455875745230850,'包子','29657d1c-0b9e-4ac9-8e2f-a41dc2648a19.jpg',1650455302660698114,1644139533027549185,NULL,NULL,1,4.00,NULL),
(1650455879490744322,'包子','29657d1c-0b9e-4ac9-8e2f-a41dc2648a19.jpg',1650455302660698114,1644139533027549185,NULL,NULL,1,4.00,NULL),
(1650649571736428545,'123','b5705d11-78fa-4bf5-8761-758807b51ef5.jpg',1650649552107085825,NULL,1650461066041257985,NULL,1,12.00,NULL),
(1650649694679867394,'123','b5705d11-78fa-4bf5-8761-758807b51ef5.jpg',1650649552107085825,NULL,1650461066041257985,NULL,1,12.00,NULL),
(1650677114610929666,'包子','29657d1c-0b9e-4ac9-8e2f-a41dc2648a19.jpg',1,1644139533027549185,NULL,NULL,7,4.00,NULL),
(1650677147053871105,'大包子','6941d87d-9f77-457d-affe-cff55c5c889b.jpg',1,1644138082201026561,NULL,'无糖',5,2.00,NULL),
(1650677162761539585,'大包子','6941d87d-9f77-457d-affe-cff55c5c889b.jpg',1,1644138082201026561,NULL,'少糖',3,2.00,NULL),
(1650677187696676866,'大包子','6941d87d-9f77-457d-affe-cff55c5c889b.jpg',1,1644138082201026561,NULL,'半糖',3,2.00,NULL),
(1650677207082749953,'大包子','6941d87d-9f77-457d-affe-cff55c5c889b.jpg',1,1644138082201026561,NULL,NULL,6,2.00,NULL),
(1650677333553598465,'qq','46118ba6-2176-43ef-ac9b-fdebe103a1ba.jpg',1,NULL,1644175926768558082,NULL,1,123.00,NULL),
(1650677376096423937,'毛氏红烧肉','4d548001-b7ba-4491-bbe0-257bec6e1b33.jpeg',1,1397850140982161409,NULL,'不要蒜,微辣',3,68.00,NULL),
(1650677397751615490,'毛氏红烧肉','4d548001-b7ba-4491-bbe0-257bec6e1b33.jpeg',1,1397850140982161409,NULL,'不要香菜,重辣',1,68.00,NULL),
(1658068259577569281,'123','b5705d11-78fa-4bf5-8761-758807b51ef5.jpg',1,NULL,1650461066041257985,NULL,3,12.00,'2023-05-15 19:14:13');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '姓名',
  `phone` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '手机号',
  `sex` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '性别',
  `id_number` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '身份证号',
  `avatar` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '头像',
  `status` int(11) DEFAULT '0' COMMENT '状态 0:禁用，1:正常',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户信息';

/*Data for the table `user` */

insert  into `user`(`id`,`name`,`phone`,`sex`,`id_number`,`avatar`,`status`) values 
(1648940092699463681,NULL,'15216565656',NULL,NULL,NULL,0),
(1648940799305469954,NULL,'15216545645',NULL,NULL,NULL,0),
(1648944780710813698,NULL,'15216853638',NULL,NULL,NULL,0),
(1648945221062369282,NULL,'15216853654',NULL,NULL,NULL,0),
(1649245227039506434,NULL,'15245856351',NULL,NULL,NULL,0),
(1649249995220729857,NULL,'15216853635',NULL,NULL,NULL,0),
(1650340127181942785,NULL,'15265478954',NULL,NULL,NULL,0),
(1650344694837698561,NULL,'15214568562',NULL,NULL,NULL,0),
(1650345553688543234,NULL,'15216856356',NULL,NULL,NULL,0),
(1650455302660698114,NULL,'15216845624',NULL,NULL,NULL,0),
(1650649552107085825,NULL,'15216856563',NULL,NULL,NULL,0),
(1650660096822267905,NULL,'15216545658',NULL,NULL,NULL,0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
