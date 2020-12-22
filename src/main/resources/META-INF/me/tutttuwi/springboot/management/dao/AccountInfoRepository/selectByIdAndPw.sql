select
    /*%expand*/*
from
    account_info
where
    user_id = /* userId */0
    and
    password = /* password */0
