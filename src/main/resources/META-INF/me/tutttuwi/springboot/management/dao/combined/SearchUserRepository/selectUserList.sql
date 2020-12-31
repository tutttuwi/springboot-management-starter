select
  info.account_id,
  info.user_id,
  indiv.fst_name,
  indiv.lst_name,
  email.email_addr,
  info.create_dt,
  info.update_dt
  from account_info info
    left join account_indiv indiv on info.account_id = indiv.account_id
    left join account_email email on info.account_id = email.account_id
  where
    /*%if @isNotEmpty(userId) */
      info.user_id = /* userId */0 and
	/*%end*/
    /*%if @isNotEmpty(fstNm) */
      indiv.fst_name = /* fstNm */0 and
    /*%end*/
    /*%if @isNotEmpty(lstNm) */
      indiv.lst_name = /* lstNm */0 and
    /*%end*/
    /*%if @isNotEmpty(emailAddr) */
      email.email_addr = /* emailAddr */0
    /*%end*/
      0=0
