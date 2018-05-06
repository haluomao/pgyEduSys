# CREATE SCHEMA `pgy` DEFAULT CHARACTER SET utf8;

USE pgy;

-- Create syntax for TABLE 'accounts'
DROP TABLE IF EXISTS `accounts`;
CREATE TABLE IF NOT EXISTS `accounts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `account_name` varchar(255) NOT NULL DEFAULT '' COMMENT '账户名',
  `account_pass` varchar(255) NOT NULL DEFAULT '' COMMENT '账户密码',
  `username` varchar(255) NOT NULL DEFAULT '' COMMENT '用户名称',
  `role` int(20) NOT NULL COMMENT '账户角色',
  `balance` bigint(20) NOT NULL DEFAULT 0 COMMENT '账户余额，单位是分*1000，仅当role为3时使用',
  `email` varchar(255) NOT NULL DEFAULT '' COMMENT '邮箱',
  `phone` varchar(255) NOT NULL DEFAULT '' COMMENT '联系电话',
  `permission` varchar(1024) NOT NULL DEFAULT '' COMMENT '权限',
  `parent_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '父账户ID',
  `account_status` int(11) NOT NULL COMMENT '账户状态',
  `begin_time` timestamp NOT NULL DEFAULT '1980-01-01 00:00:00' COMMENT '生效开始',
  `end_time` timestamp NOT NULL DEFAULT '1980-01-01 00:00:00' COMMENT '生效结束',
  `create_time` timestamp NOT NULL DEFAULT '1980-01-01 00:00:00',
  `update_time` timestamp NOT NULL DEFAULT '1980-01-01 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `account_name` (`account_name`),
  KEY `update_time` (`update_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'account_config'
DROP TABLE IF EXISTS `account_config`;
CREATE TABLE IF NOT EXISTS `account_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `account_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '关联admin账户',
  `teacher_limit` int(20) NOT NULL DEFAULT 0 COMMENT '老师账户个数上限',
  `parent_limit` int(20) NOT NULL DEFAULT 0 COMMENT '家长账户个数上限',
  `storage_limit` int(20) NOT NULL DEFAULT 0 COMMENT '存储空间上限(Byte)',
  `storage_used` int(20) NOT NULL DEFAULT 0 COMMENT '存储空间已使用',
  `config` varchar(1024) NOT NULL DEFAULT '' COMMENT '其他说明',
  `status` int(11) NOT NULL COMMENT '配置状态',
  `begin_time` timestamp NOT NULL DEFAULT '1980-01-01 00:00:00' COMMENT '生效开始',
  `end_time` timestamp NOT NULL DEFAULT '1980-01-01 00:00:00' COMMENT '生效结束',
  `create_time` timestamp NOT NULL DEFAULT '1980-01-01 00:00:00',
  `update_time` timestamp NOT NULL DEFAULT '1980-01-01 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `account_id` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'categories'
DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `name` VARCHAR(255) NOT NULL COMMENT '科目/分类名称',
  `description` VARCHAR(1020) NOT NULL DEFAULT '' COMMENT '分类描述',
  `price` bigint(20) NOT NULL DEFAULT 0 COMMENT '类别价格:元*100000',
  `user_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '所属账户ID',
  `status` int(11) NOT NULL COMMENT '分类状态',
  `create_time` TIMESTAMP NOT NULL DEFAULT '1980-01-01 00:00:00',
  `update_time` TIMESTAMP NOT NULL DEFAULT '1980-01-01 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'materials'
DROP TABLE IF EXISTS `materials`;
CREATE TABLE `materials` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `name` VARCHAR(255) NOT NULL COMMENT '材料名称',
  `description` VARCHAR(1020) NOT NULL DEFAULT '' COMMENT '材料描述',
  `file_type` int(11) NOT NULL DEFAULT 0 COMMENT '材料类型: 0-txt,1-ppt',
  `url` VARCHAR(1020) NOT NULL DEFAULT '' COMMENT '材料下载链接',
  `icon` VARCHAR(1020) NOT NULL DEFAULT '' COMMENT '材料缩略图链接',
  `author_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '材料作者',
  `uploader_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '材料上传者',
  `status` int(11) NOT NULL COMMENT '状态',
  `public_level` int(11) NOT NULL COMMENT '公开等级',
  `create_time` TIMESTAMP NOT NULL DEFAULT '1980-01-01 00:00:00',
  `update_time` TIMESTAMP NOT NULL DEFAULT '1980-01-01 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY(`name`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'category'
DROP TABLE IF EXISTS `category_material`;
CREATE TABLE `category_material` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `category_id` INT UNSIGNED NOT NULL COMMENT '分类ID',
  `material_id` INT UNSIGNED NOT NULL COMMENT '材料ID',
  `status` int(11) NOT NULL COMMENT '状态',
  `create_time` TIMESTAMP NOT NULL DEFAULT '1980-01-01 00:00:00',
  `update_time` TIMESTAMP NOT NULL DEFAULT '1980-01-01 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'grades'
DROP TABLE IF EXISTS `grades`;
CREATE TABLE `grades` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `grade_name` VARCHAR(255) NOT NULL COMMENT '年级名称',
  `description` VARCHAR(1020) NOT NULL DEFAULT '' COMMENT '年级描述',
  `status` int(11) NOT NULL COMMENT '状态',
  `create_time` TIMESTAMP NOT NULL DEFAULT '1980-01-01 00:00:00',
  `update_time` TIMESTAMP NOT NULL DEFAULT '1980-01-01 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'grade_material'
DROP TABLE IF EXISTS `grade_material`;
CREATE TABLE `grade_material` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `grade_id` INT UNSIGNED NOT NULL COMMENT '年级ID',
  `material_id` INT UNSIGNED NOT NULL COMMENT '材料ID',
  `status` int(11) NOT NULL COMMENT '状态',
  `create_time` TIMESTAMP NOT NULL DEFAULT '1980-01-01 00:00:00',
  `update_time` TIMESTAMP NOT NULL DEFAULT '1980-01-01 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
