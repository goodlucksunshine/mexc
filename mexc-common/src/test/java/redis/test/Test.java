package redis.test;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Test {

	 private static JedisPool pool = null;

	    /** Redis的端口号 **/
	    private static int PORT = 6379;

	    /**
	     * 可用连接实例的最大数目，默认值为8
	     * 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	     */
	    private static int MAX_ACTIVE = 1024;

	    /** 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。 **/
	    private static int MAX_IDLE = 200;

	    /** 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException； **/
	    private static int MAX_WAIT = 10000;
	    /** 超时时间 **/
	    private static int TIMEOUT = 10000;

	    /** 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的 **/
	    private static boolean TEST_ON_BORROW = true;

	    static {
	        try {
	            JedisPoolConfig config = new JedisPoolConfig();
	            config.setMaxActive(MAX_ACTIVE);
	            config.setMaxIdle(MAX_IDLE);
	            config.setMaxWait(MAX_WAIT);
	            config.setTestOnBorrow(TEST_ON_BORROW);
	            pool = new JedisPool(config, "192.168.0.100", PORT, TIMEOUT);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    public static Jedis getJedis() {

	        return pool.getResource();
	    }
	    public static void main(String[] args) {
//			getJedis().del("ecc1628c353c660139e093c8442de085");
	    	//test1();
			String json=getJedis().srandmember("BTC_ADDRESS_LIB_NOT_USED");
//			JSON.parseObject(json,MexcAdd)
	    
		}
	    
	    public static void test1(){
	    	
	    	
	    	
	   	 System.out.println("======================zset=========================="); 
         // 清空数据 
         
         System.out.println("=============增=============");
         
         System.out.println("zset中添加元素element002："+getJedis().zadd("zset", 2.0 ,"element002"));
         System.out.println("zset中添加元素element003："+getJedis().zadd("zset", 2.0, "element003")); 
         System.out.println("zset中添加元素element004："+getJedis().zadd("zset", 3.000000211, "element004"));
         System.out.println("zset中添加元素element002："+getJedis().zadd("zset", 3.1, "element0031")); 
         System.out.println("zset集合中的所有元素："+getJedis().zrange("zset", 0, -1));//按照权重值排序
         
         System.out.println("zset中添加元素element001："+getJedis().zadd("zset", 7.0, "element007")); 
         System.out.println("zset中添加元素element001："+getJedis().zadd("zset", 7.0, "element008"));
         System.out.println("zset中添加元素element001："+getJedis().zadd("zset", 7.0, "element001")); 
         
        // System.out.println("=============删=============");
        // System.out.println("zset中删除元素element002："+getJedis().zrem("zset", "element0asdasdasd"));
        // System.out.println("zset集合中的所有元素："+getJedis().zrange("zset", 0, -1));
         System.out.println();
         
         System.out.println("=============改=============");
         //System.out.println("zset中添加元素element001："+getJedis().zadd("zset", 4.0, "element001")); 
         
         System.out.println("=============查=============");
         System.out.println("统计zset集合中的元素中个数："+getJedis().zcard("zset"));
         System.out.println("统计zset集合中权重某个范围内（1.0——5.0），元素的个数："+getJedis().zcount("zset", 2.0, 5.0));
         
         System.out.println("统计zset集合中权重某个范围内（1.0——5.0），元素："+getJedis().zrangeByScore("zset", 1.0, 5.0));
         System.out.println("统计zset集合中权重某个范围内（6.0——8.0），元素："+getJedis().zrangeByScore("zset", 6.0, 8.0));
         System.out.println("查看zset集合中element004的权重："+getJedis().zscore("zset", "element004"));
        // System.out.println("查看下标1到2范围内的元素值："+getJedis().zrange("zset", 1, 2));
	    }
}
