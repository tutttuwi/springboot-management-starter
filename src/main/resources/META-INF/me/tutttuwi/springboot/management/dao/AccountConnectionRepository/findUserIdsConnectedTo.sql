select
  user_id
  from account_connection
  where provider_id = /* providerId */0
    and provider_user_id in /* providerUserId */(1,2,3)
