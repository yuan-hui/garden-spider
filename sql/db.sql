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