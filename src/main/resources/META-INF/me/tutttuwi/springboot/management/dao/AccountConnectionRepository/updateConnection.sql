update account_connection
  set display_name = /* accountConnection.displayName */0,
    profile_url = /* accountConnection.profileUrl */0,
    image_url = /* accountConnection.imageUrl */0,
    access_token = /* accountConnection.accessToken */0,
    secret = /* accountConnection.secret */0,
    refresh_token = /* accountConnection.refreshToken */0,
    expire_time = /* accountConnection.expireTime */0
  where user_id =  /* accountConnection.userId */0
    and provider_id = /* accountConnection.providerId */0
    and provider_user_id = /* accountConnection.providerUserId */0
