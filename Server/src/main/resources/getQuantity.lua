--
-- Created by IntelliJ IDEA.
-- User: ASUS
-- Date: 2019/3/5
-- Time: 15:43
-- To change this template use File | Settings | File Templates.
--
-- userid  orderid  itemid
local num=redis.call("get","cart:"..KEYS[1]..":"..KEYS[2]..":"..KEYS[3])
return num

