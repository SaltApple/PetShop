--
-- Created by IntelliJ IDEA.
-- User: ASUS
-- Date: 2019/2/21
-- Time: 12:24
-- To change this template use File | Settings | File Templates.
--
local a=redis.call("exists",KEYS[1])
local s;
if a==1 then--是用户 未过期
    s=redis.call("get",KEYS[1])
else
    s="gc"
end
return s
