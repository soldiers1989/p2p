Version for MYSQL 5.6.21

-- 表结构脚本(标准版)

注： 若数据库存在变动，应及时更新SVN上脚本版本或交付时作全局更新。
    
PS: 
	去除注释简单正则:
	替换先后顺序(不包含中文括弧)
	
	1> 字段（\sCOMMENT '.*'）替换为（）
	2> 表（\sCOMMENT='.*'）替换为（）
	3> 函数、事件及其他注释（--\s.*）替换为（）
	4> （AUTO_INCREMENT=\d+）替换为（AUTO_INCREMENT=1）
		
		
