import sqlitescan
import reporting

interesting_tables = sqlitescan.get_interesting_tables()

for db in interesting_tables.keys():
	print('Processing database %s' % db)

	for table in interesting_tables[db]:
		print('Processing table %s' % table)

		interesting_rows = sqlitescan.get_interesting_rows(db, table)
		reporting.write_report_file(db, table, interesting_rows)
