/*==============================================================*/
/* DBMS name:      MySQL 5.7          asd                           */
/*==============================================================*/


drop table if exists tb_goods;

drop table if exists tb_goods_sku;

drop table if exists tb_order;

drop table if exists tb_pay_repeat;

drop table if exists tb_user;

/*==============================================================*/
/* Table: tb_goods                                              */
/*==============================================================*/
create table tb_goods
(
   goods_id             varchar(36) not null comment '商品id',
   user_id              varchar(36) comment '用户id',
   name                 varchar(36) comment '商品名称',
   title                varchar(200) comment '商品标题',
   goods_desc           varchar(400) comment '商品描述',
   start_time           timestamp null comment '活动开始时间',
   end_time             timestamp null comment '活动结束时间',
   primary key (goods_id)
);

/*==============================================================*/
/* Index: Index_goods_name_title_desc                           */
/*==============================================================*/
create index Index_goods_name_title_desc on tb_goods
(
   name,
   title,
   goods_desc
);

/*==============================================================*/
/* Table: tb_goods_sku                                          */
/*==============================================================*/
create table tb_goods_sku
(
   sku_id               varchar(36) not null,
   goods_id             varchar(36) comment '商品id',
   sku_params           text comment '规格参数',
   sku_reserve          int(10) comment '目前库存',
   sku_start_reserve    int(10) comment '开始库存',
   price                decimal(10,2) comment '价格',
   picture              varchar(300) comment '商品图片',
   state                tinyint comment '状态',
   version              int(10),
   primary key (sku_id)
);
create index Index_goods_sku_goods_id on tb_goods_sku
(
   goods_id
);

/*==============================================================*/
/* Table: tb_order                                              */
/*==============================================================*/
create table tb_order
(
   order_id             varchar(36) not null comment '订单id',
   user_id              varchar(36) comment '用户id',
   sku_id               varchar(36),
   take_time            timestamp NULL DEFAULT CURRENT_TIMESTAMP comment '下单时间',
   goods_name           varchar(36) comment '商品名称，用于查询订单时简单显示',
   pay_time             timestamp null comment '支付时间',
   pay_price            decimal(10,2) comment '支付价格',
   goods_num            int(10) comment '商品数量',
   goods_price_per      decimal(10,2) comment '商品单价',
   state                tinyint comment '订单状态',
   version              int(10),
   primary key (order_id)
);

/*==============================================================*/
/* Index: Index_order_goods_name                                */
/*==============================================================*/
create index Index_order_goods_name on tb_order
(
   goods_name
);

/*==============================================================*/
/* Table: tb_pay_repeat                                       */
/*==============================================================*/
create table tb_pay_repeat
(
   order_id             varchar(36) comment '订单id',
   primary key (order_id)
);

alter table tb_pay_repeat comment '支付防重表';

/*==============================================================*/
/* Table: tb_user                                               */
/*==============================================================*/
create table tb_user
(
   user_id              varchar(36) not null comment '用户id',
   username             varchar(18) not null comment '账号',
   password             varchar(100) not null comment '密码',
   nickname             varchar(20) comment '昵称',
   salt                 varchar(10) comment '盐',
   amount               decimal(10,2) comment '余额',
   state                tinyint comment '状态',
   version              int(10),
   primary key (user_id)
);

alter table tb_user comment '用户表，简单处理，不分卖主和买主';



DELETE from tb_user;
insert into tb_user VALUES('u1','boss','$2a$10$rv2ASvpB2Z01SlAcEhngdunz9JA8Rdyt8HF0nCBUQER4mLM4xyh/S','Boss','a1a2a',0,1,1);
insert into tb_user VALUES('u2','aaa','$2a$10$rv2ASvpB2Z01SlAcEhngdunz9JA8Rdyt8HF0nCBUQER4mLM4xyh/S','user_a','dasv1',1000,1,1);
insert into tb_user VALUES('u3','bbb','$2a$10$rv2ASvpB2Z01SlAcEhngdunz9JA8Rdyt8HF0nCBUQER4mLM4xyh/S','user_b','rgae1',1000,1,1);
insert into tb_user VALUES('u4','ccc','$2a$10$rv2ASvpB2Z01SlAcEhngdunz9JA8Rdyt8HF0nCBUQER4mLM4xyh/S','user_c','rfsa3',1000,1,1);
insert into tb_user VALUES('u5','ddd','$2a$10$rv2ASvpB2Z01SlAcEhngdunz9JA8Rdyt8HF0nCBUQER4mLM4xyh/S','user_d','rf3a3',2,1,1);

delete from tb_goods;
insert into tb_goods VALUES('g1','u1','百味海苔','海苔芝麻虾脆夹心脆','虾脆海苔脆片 紫菜','2019-10-1 00:00:00','2020-10-1 00:00:00');
insert into tb_goods VALUES('g2','u1','蒙古牛肉干','风干牛肉干西藏特产内蒙古手撕耗牛肉干','手工传承 古法制作 有品质的牦牛肉干','2019-10-1 00:00:00','2020-1-1 00:00:00');
insert into tb_goods VALUES('g3','u1','三只松鼠','三只松鼠_每日坚果','混合果仁零食','2019-10-1 00:00:00','2020-1-1 00:00:00');
insert into tb_goods VALUES('g4','u1','元香辣鱼排','味芝元香辣鱼排','鱼块即食麻辣小吃','2019-10-1 00:00:00','2020-1-1 00:00:00');
insert into tb_goods VALUES('g5','u1','夹心饼干','焦糖夹心饼干','各式黑糖麦芽焦糖夹心饼干','2020-2-1 00:00:00','2020-10-1 00:00:00');

DELETE from tb_goods_sku;
insert into tb_goods_sku VALUES('sku1','g1','{"口味":"虾味";"重量":"100g"}',20,20,2.50,'pic,pac,',1,1);
insert into tb_goods_sku VALUES('sku2','g1','{"口味":"虾味";"重量":"150g"}',15,15,3.50,'pic,pac,',1,1);
insert into tb_goods_sku VALUES('sku3','g1','{"口味":"紫菜味";"重量":"150g"}',30,30,3.00,'pic,pac,',1,1);
insert into tb_goods_sku VALUES('sku4','g2','{"口味":"微辣";"重量":"150g"}',100,100,30.00,'pic,pac,',1,1);
insert into tb_goods_sku VALUES('sku5','g2','{"口味":"麻辣";"重量":"150g"}',50,50,32.00,'pic,pac,',1,1);
insert into tb_goods_sku VALUES('sku6','g3','{"重量":"50g"}',200,200,1.00,'pic,pac,',1,1);
insert into tb_goods_sku VALUES('sku7','g3','{"重量":"150g"}',10,10,2.00,'pic,pac,',1,1);
insert into tb_goods_sku VALUES('sku8','g4','{"口味":"香辣";"条数/包":"10"}',50,50,3.00,'pic,pac,',1,1);
insert into tb_goods_sku VALUES('sku9','g4','{"口味":"麻辣";"条数/包":"15"}',50,50,5.00,'pic,pac,',1,1);
insert into tb_goods_sku VALUES('sku10','g5','{"口味":"港式"}',50,50,5.00,'pic,pac,',1,1);
insert into tb_goods_sku VALUES('sku11','g5','{"口味":"法式"}',50,50,5.00,'pic,pac,',1,1);