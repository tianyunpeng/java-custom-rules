class MyClass {
    MyClass(MyClass mc) { }
    // 测试方法1
    @RequestMapping(value = "/PT/business/settings/ywlbqd$m=query.service", method = RequestMethod.POST)
    @ResponseBody
    public Object testString(@RequestBody String bean){
        System.out.println("测试方法入参bean"+bean);
        return null;
    }
    // 测试方法2,完全符合规范的一个方法
    @RequestMapping(value = "/PT/business/settings/ywlbqd$m=query.service", method = RequestMethod.POST)
    @ResponseBody
    public ObjectBean testObjectBean(@RequestBody ObjectBean bean){
        return null;
    }

    // 测试方法3
    @RequestMapping(value = "/PT/business/settings/ywlbqd$m=query.service", method = RequestMethod.POST)
    @ResponseBody
    public Object testMap(@RequestBody Map bean){
        return null;
    }

    // 测试方法4
    @RequestMapping(value = "/PT/business/settings/ywlbqd$m=query.service", method = RequestMethod.POST)
    @ResponseBody
    public Object testJSONObject(@RequestBody JSONObject bean){
        return null;
    }
}