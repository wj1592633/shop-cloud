/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/*==============================================================*/


drop table if exists tb_goods;

drop table if exists tb_goods_sku;

drop table if exists tb_order;

drop table if exists tb_rab_order_goods;

drop table if exists tb_rab_order_oder;

drop table if exists tb_user;

/*==============================================================*/
/* Table: tb_goods                                              */
/*==============================================================*/
create table tb_goods
(
   goods_id             varchar(36) not null comment '��Ʒid',
   user_id              varchar(36) comment '�û�id',
   name                 varchar(36) comment '��Ʒ����',
   title                varchar(200) comment '��Ʒ����',
   goods_desc           text comment '��Ʒ����',
   start_time           timestamp comment '���ʼʱ��',
   end_time             timestamp comment '�����ʱ��',
   primary key (goods_id)
);
alter table tb_goods add index index_tb_goods_name_title(name,title);
/*==============================================================*/
/* Table: tb_goods_sku                                          */
/*==============================================================*/
create table tb_goods_sku
(
   sku_id               varchar(36) not null,
   goods_id             varchar(36) comment '��Ʒid',
   sku_params           text comment '������',
   sku_reserve          int(10) comment 'Ŀǰ���',
   sku_start_reserve    int(10) comment '��ʼ���',
   price                decimal(10,2) comment '�۸�',
   picture              varchar(300) comment '��ƷͼƬ',
   state                tinyint comment '״̬',
   version              int(10),
   primary key (sku_id)
);

/*==============================================================*/
/* Table: tb_order                                              */
/*==============================================================*/
create table tb_order
(
   order_id             varchar(36) not null comment '����id',
   user_id              varchar(36) comment '�û�id',
   sku_id               varchar(36),
   take_time            timestamp comment '�µ�ʱ��',
   pay_time             timestamp comment '֧��ʱ��',
   pay_price            decimal(10,2) comment '֧���۸�',
   goods_num            int(10) comment '��Ʒ����',
   goods_price_per      decimal(10,2) comment '��Ʒ����',
   state                tinyint comment '����״̬',
   version              int(10),
   primary key (order_id)
);

/*==============================================================*/
/* Table: tb_rab_order_goods                                    */
/*==============================================================*/
create table tb_rab_order_goods
(
   user_id              varchar(36) not null comment '�û�id',
   sku_id               varchar(36) not null,
   order_time           timestamp,
   state                tinyint comment '״̬',
   primary key (user_id, sku_id)
);

alter table tb_rab_order_goods comment '��Ʒģ����µ�rabbit������';

/*==============================================================*/
/* Table: tb_rab_order_oder                                     */
/*==============================================================*/
create table tb_rab_order_oder
(
   user_id              varchar(36) comment '�û�id',
   sku_id               varchar(36)
);

alter table tb_rab_order_oder comment '����ģ����µ�rabbit����������';

/*==============================================================*/
/* Table: tb_user                                               */
/*==============================================================*/
create table tb_user
(
   user_id              varchar(36) not null comment '�û�id',
   username             varchar(18) not null comment '�˺�',
   password             varchar(100) not null comment '����',
   nickname             varchar(20) comment '�ǳ�',
   salt                 varchar(10) comment '��',
   amount               decimal(10,2) comment '���',
   state                tinyint comment '״̬',
   version              int(10),
   primary key (user_id)
);

alter table tb_user comment '�û����򵥴�����������������';

create table tb_stop_repeat
(
   user_id              varchar(36) not null comment '�û�id',
   sku_id               varchar(36) not null,
   order_time           timestamp,
   primary key (user_id, sku_id)
);
alter table tb_stop_repeat comment '�µ�ǰ,��ֹ�ظ���ɱ��';
ALTER table tb_goods_sku add index index_sku_goodsId(goods_id);
DELETE from tb_user;
insert into tb_user VALUES('u1','boss','$10$0J6lA5ola00wEWtayA.cX.SkQSyLil71vBFHdnbP6lkmk40.NHXGy','Boss','a1a2a',0,1,1);
insert into tb_user VALUES('u2','aaa','$10$0J6lA5ola00wEWtayA.cX.SkQSyLil71vBFHdnbP6lkmk40.NHXGy','user_a','dasv1',1000,1,1);
insert into tb_user VALUES('u3','bbb','$10$0J6lA5ola00wEWtayA.cX.SkQSyLil71vBFHdnbP6lkmk40.NHXGy','user_b','rgae1',1000,1,1);
insert into tb_user VALUES('u4','ccc','$10$0J6lA5ola00wEWtayA.cX.SkQSyLil71vBFHdnbP6lkmk40.NHXGy','user_c','rfsa3',1000,1,1);
insert into tb_user VALUES('u5','ddd','$10$0J6lA5ola00wEWtayA.cX.SkQSyLil71vBFHdnbP6lkmk40.NHXGy','user_d','rf3a3',2,1,1);

delete from tb_goods;
insert into tb_goods VALUES('g1','u1','��ζ��̦','��̦֥��Ϻ����Ĵ�','Ϻ�ຣ̦��Ƭ �ϲ�','2019-10-1 00:00:00','2020-10-1 00:00:00');
insert into tb_goods VALUES('g2','u1','�ɹ�ţ���','���ţ��������ز����ɹ���˺��ţ���','�ֹ����� �ŷ����� ��Ʒ�ʵ���ţ���','2019-10-1 00:00:00','2020-1-1 00:00:00');
insert into tb_goods VALUES('g3','u1','��ֻ����','��ֻ����_ÿ�ռ��','��Ϲ�����ʳ','2019-10-1 00:00:00','2020-1-1 00:00:00');
insert into tb_goods VALUES('g4','u1','Ԫ��������','ζ֥Ԫ��������','��鼴ʳ����С��','2019-10-1 00:00:00','2020-1-1 00:00:00');
insert into tb_goods VALUES('g5','u1','���ı���','���Ǽ��ı���','��ʽ������ѿ���Ǽ��ı���','2020-2-1 00:00:00','2020-10-1 00:00:00');

DELETE from tb_goods_sku;
insert into tb_goods_sku VALUES('sku1','g1','{"��ζ":"Ϻζ";"����":"100g"}',20,20,2.50,'pic,pac,',1,1);
insert into tb_goods_sku VALUES('sku2','g1','{"��ζ":"Ϻζ";"����":"150g"}',15,15,3.50,'pic,pac,',1,1);
insert into tb_goods_sku VALUES('sku3','g1','{"��ζ":"�ϲ�ζ";"����":"150g"}',30,30,3.00,'pic,pac,',1,1);
insert into tb_goods_sku VALUES('sku4','g2','{"��ζ":"΢��";"����":"150g"}',100,100,30.00,'pic,pac,',1,1);
insert into tb_goods_sku VALUES('sku5','g2','{"��ζ":"����";"����":"150g"}',50,50,32.00,'pic,pac,',1,1);
insert into tb_goods_sku VALUES('sku6','g3','{"����":"50g"}',200,200,1.00,'pic,pac,',1,1);
insert into tb_goods_sku VALUES('sku7','g3','{"����":"150g"}',10,10,2.00,'pic,pac,',1,1);
insert into tb_goods_sku VALUES('sku8','g4','{"��ζ":"����";"����/��":"10"}',50,50,3.00,'pic,pac,',1,1);
insert into tb_goods_sku VALUES('sku9','g4','{"��ζ":"����";"����/��":"15"}',50,50,5.00,'pic,pac,',1,1);
insert into tb_goods_sku VALUES('sku10','g5','{"��ζ":"��ʽ"}',50,50,5.00,'pic,pac,',1,1);
insert into tb_goods_sku VALUES('sku11','g5','{"��ζ":"��ʽ"}',50,50,5.00,'pic,pac,',1,1);









