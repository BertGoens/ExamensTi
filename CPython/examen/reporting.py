import os
import socket
import time
import pysqlite2.dbapi2 as sqlite
from contextlib import closing

def write_report_file(file, table, interesting_rows):
	dest = open('dest.txt').read()
	filename = os.path.basename(dest + "\\" + file)
	(filenamewithoutext, ext) = os.path.splitext(filename)
	fd = open(filenamewithoutext + '.txt', 'a')
	fd.write(socket.gethostname().capitalize() + "\n")
	fd.write(file.capitalize() + "\n")
	fd.write(time.strftime("%Y-%m-%d %H:%M") + "\n")
	fd.write(table.capitalize() + "\n")
	for row in get_table_dump(file, table):
		for column in row:
			fd.write(str(column) + "\\")
		fd.write("\n")
	fd.close()
	
def get_table_dump(file, table):
	with closing(sqlite.connect(file)) as con:
		cur = con.cursor()
		cur.execute('SELECT * FROM ' + table)
		for row in cur.fetchall():
			yield row