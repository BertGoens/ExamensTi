import os
import pysqlite2.dbapi2 as sqlite
import sys

def get_interesting_tables():
	interesting_tables = []
	for root, dirs, files in os.walk('C:\\'):
		for file in files:
			(root, ext) = os.path.splitext(file)
			if(ext == '.sqlite'):
				db = {}
				db[file] = []
				
				con = sqlite.connect(root + file)
				cur = con.cursor()
				cur.execute('SELECT tbl_name FROM sqlite_master WHERE type = \'table\'')
				
				for row in cur.fetchall():
					db[file].append(row[0])
				interesting_tables.append(db)
	cur.close()
	con.close()
	return interesting_tables

def get_interesting_rows(file, table):
	keywords = load_keywords()
	interesting_rows = []
	con = sqlite.connect(file)
	cur = con.cursor()
	for keyword in keywords:
		try:
			cur.execute('SELECT ' + keyword + ' FROM ' + table)
			for row in cur.fetchone():
				interesting_rows.append(keyword)
		except:
			None
	cur.close()
	con.close()
	
	return interesting_rows

def load_keywords():
	keywords = []
	if os.path.isfile('keywords.txt'):
		for line in open('keywords.txt').readlines():
			keyword=line.strip('\n').strip('\r').lower()
			keywords.append(keyword)
	else:
		keywords += ['pass', 'login', 'user', 'ip', 'hash', 'server', 'host']
	
	return keywords