--
-- Created by IntelliJ IDEA.
-- User: ASUS
-- Date: 2019/3/5
-- Time: 11:11
-- To change this template use File | Settings | File Templates.
--
local mes=redis.call("get","maxid:"..KEYS[1])
return mes

