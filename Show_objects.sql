SELECT table_name, table_schema, table_type FROM information_schema.tables WHERE table_schema = 'auja9067';
SELECT * FROM pg_catalog.pg_views WHERE viewowner = 'auja9067';
SELECT * FROM pg_matviews WHERE schemaname = 'auja9067';
SELECT trigger_catalog,
       trigger_schema,
       trigger_name,
       event_manipulation,
       event_object_catalog,
       event_object_schema,
       event_object_table,
       action_order,
       action_condition,
       action_statement,
       action_orientation,
       action_timing 
FROM information_schema.triggers;
SELECT  specific_catalog, specific_schema, specific_name, routine_catalog, routine_schema, routine_name, routine_type, data_type, type_udt_catalog, type_udt_schema, routine_definition FROM information_schema.routines WHERE specific_schema = 'auja9067';
SELECT * FROM pg_catalog.pg_indexes WHERE schemaname = 'auja9067';