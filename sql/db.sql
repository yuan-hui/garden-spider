CREATE TABLE `t_content` (
  `id` varchar(255) NOT NULL COMMENT '主键ID',
  `cid` varchar(255) NOT NULL COMMENT '来源内容ID',
  `title` varchar(500) NOT NULL COMMENT '标题',
  `midiameter` varchar(255) DEFAULT NULL COMMENT '米径(cm)',
  `height` varchar(255) DEFAULT NULL COMMENT '高度(cm)',
  `crown` varchar(255) DEFAULT NULL COMMENT '冠幅(cm)',
  `grounddiameter` varchar(255) DEFAULT NULL COMMENT '地径(cm)',
  `unit` varchar(255) DEFAULT NULL COMMENT '单位',
  `price` varchar(255) DEFAULT NULL COMMENT '价格',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `company` varchar(255) DEFAULT NULL COMMENT '公司',
  `province` varchar(255) DEFAULT NULL COMMENT '省份',
  `city` varchar(255) DEFAULT NULL COMMENT '城市',
  `contacts` varchar(255) DEFAULT NULL COMMENT '联系人',
  `tel` varchar(255) DEFAULT NULL COMMENT '电话',
  `fax` varchar(255) DEFAULT NULL COMMENT '传真',
  `email` varchar(255) DEFAULT NULL COMMENT '电子邮箱',
  `website` varchar(255) DEFAULT NULL COMMENT '网址',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `zipcode` varchar(255) DEFAULT NULL COMMENT '邮编',
  `releasetime` datetime DEFAULT NULL COMMENT '发布时间',
  `detailId` varchar(255) NOT NULL COMMENT '详情页ID',
  `source` varchar(500) NOT NULL COMMENT '来源网站',
  `code` varchar(255) NOT NULL COMMENT '所属分类或栏目',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8


CREATE TABLE `t_downloader_config` (
  `id` varchar(255) NOT NULL COMMENT '主键ID',
  `url` varchar(255) NOT NULL COMMENT '列表页通用访问路径',
  `startpage` int(11) NOT NULL DEFAULT '0' COMMENT '开始页',
  `endpage` int(11) NOT NULL DEFAULT '0' COMMENT '结束页',
  `status` varchar(255) NOT NULL DEFAULT 'no' COMMENT '状态编码(yes/no)',
  `code` varchar(255) NOT NULL COMMENT '业务编码(gooood-architecture)',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `t_page_detail` (
  `id` varchar(255) NOT NULL COMMENT '主键ID',
  `url` varchar(255) NOT NULL COMMENT '详情页地址',
  `code` varchar(500) NOT NULL COMMENT '所属分类或栏目',
  `path` varchar(255) DEFAULT NULL COMMENT '文件所在路径(相对路径)',
  `download` varchar(255) NOT NULL DEFAULT 'no' COMMENT '是否已下载到本地(yes/no)',
  `parser` varchar(255) NOT NULL DEFAULT 'no' COMMENT '分析数据(yes/no)',
  `pageno` int(11) DEFAULT NULL COMMENT '列表页码',
  `source` varchar(500) DEFAULT NULL COMMENT '来源地址(列表页地址)',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8


CREATE TABLE `t_page_list` (
  `id` varchar(255) NOT NULL COMMENT '主键ID',
  `url` varchar(255) NOT NULL COMMENT '列表页URL',
  `pageno` int(11) NOT NULL DEFAULT '0' COMMENT '第几页',
  `code` varchar(255) NOT NULL COMMENT '所属分类或栏目',
  `handle` varchar(255) NOT NULL DEFAULT 'no' COMMENT '是否已分析成详情页(yes/no)',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `price_province` (
  `id` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '主键ID',
  `areaNo` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '地区编号',
  `areaName` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '地区名称',
  PRIMARY KEY (`id`),
  KEY `_areaName` (`areaName`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='地区表';

CREATE TABLE `price_product` (
  `id` varchar(255) CHARACTER SET utf8 NOT NULL,
  `productName` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '产品名称',
  `breedName` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '品种名',
  `area` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '地区',
  `areaNo` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '地区编号',
  `op` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '状态',
  `miDiameterMax` double(10,2) DEFAULT '0.00' COMMENT '胸径/米径(cm)最大值',
  `miDiameterMin` double(10,2) DEFAULT '0.00' COMMENT '胸径/米径(cm)最小值',
  `totalPrice` double(10,2) DEFAULT '0.00' COMMENT '到货价',
  `source` varchar(2) CHARACTER SET utf8 DEFAULT NULL COMMENT '数据源',
  `details` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '详情',
  `supplier` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '供应商',
  `tel` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '电话',
  `contacts` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '联系人',
  `heightMax` double(10,2) DEFAULT '0.00' COMMENT '高度(cm)最大值',
  `heightMin` double(10,2) DEFAULT '0.00' COMMENT '高度(cm)最大值',
  `crownMax` double(10,2) DEFAULT '0.00' COMMENT '冠幅(cm)最大值',
  `crownMin` double(10,2) DEFAULT '0.00' COMMENT '冠幅(cm)最小值',
  `updateTime` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新时间',
  `createTime` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '完成时间',
  `startingFare` double(65,2) DEFAULT '0.00' COMMENT '上车价',
  `invoiceType` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '发票类型',
  `importStatus` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '导入状态(N-未导入 Y-已导入)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='产品表';

CREATE TABLE `good_standard_name` (
  `id` char(32) COLLATE utf8_bin NOT NULL COMMENT '主键ID',
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `goodVarietiesID` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '品种',
  `alias` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '别名(多个用"|"分隔)',
  `latin` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '拉丁文',
  `imageId` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '图片ID',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '情详',
  `usageHabits` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '习性用途',
  `lastModified` datetime DEFAULT NULL COMMENT '最后修改时间',
  `updateBy` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '最后修改人',
  `ip` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '最后修改人ip',
  PRIMARY KEY (`id`),
  KEY `alias` (`alias`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='标准产品库';