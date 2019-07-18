--
-- Created by IntelliJ IDEA.
-- User: ASUS
-- Date: 2019/3/5
-- Time: 16:08
-- To change this template use File | Settings | File Templates.
--
redis.call("set","cart:"..KEYS[1]..":"..KEYS[2]..":"..KEYS[3],KEYS[4])

