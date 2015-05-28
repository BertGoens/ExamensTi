import os
import pysqlite2.dbapi2 as sqlite3
import sys

def fetch_cookies(path = 'C:\\Users\\Tim\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\nn2ud9in.default\\'):
	for root, dirs, files in os.walk(path):
		for file in files:
			(root, ext) = os.path.splitext(file)
			if(ext == '.sqlite' or ext == '.sqlite3'):
				con = sqlite3.connect(file)
				cur = con.cursor()
				try:
					cur.execute('SELECT * FROM moz_cookies')
					for row in cur.fetchall():
						print(row)
				except Exception:
					None

def fetch_search_queries(path = 'C:\\Users\\Tim\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\nn2ud9in.default\\'):
	queries = []
	for root, dirs, files in os.walk(path):
		for file in files:
			(root, ext) = os.path.splitext(file)
			if(ext == '.sqlite' or ext == '.sqlite3'):
				con = sqlite3.connect(file)
				cur = con.cursor()
				try:
					cur.execute("SELECT url FROM moz_places WHERE url LIKE 'https://www.google.com/search?q=%'")
					for row in cur.fetchall():
						params = row[0].split('=')[1].split('&')
						queries += params[0].split('+')
				except Exception:
					None
	return queries

if __name__ == '__main__':
	if len(sys.argv) < 2:
		print(' '.join(fetch_search_queries()))
	else:
		print(' '.join(fetch_search_queries(sys.argv[1])))