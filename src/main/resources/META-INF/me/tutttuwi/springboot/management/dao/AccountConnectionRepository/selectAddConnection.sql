select
  coalesce(max(rank) + 1, 1) as rank
  from
    account_connection
  where user_id = /* userId */0
    and provider_id = /* providerId */0
