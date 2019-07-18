--
-- Created by IntelliJ IDEA.
-- User: ASUS
-- Date: 2019/2/20
-- Time: 20:35
-- To change this template use File | Settings | File Templates.
--
--
local v=redis.call("exists",KEYS[1])
if v==0 then
    --
    redis.call("set",KEYS[1],KEYS[2])
end

