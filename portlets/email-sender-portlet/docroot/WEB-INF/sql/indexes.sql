create index IX_E49DD145 on RL_Template (groupId);
create index IX_DD10F10F on RL_Template (uuid_);
create index IX_E68123B9 on RL_Template (uuid_, companyId);
create unique index IX_EB41447B on RL_Template (uuid_, groupId);