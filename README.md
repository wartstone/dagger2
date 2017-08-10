# dagger2
自定义注解自动生成component, module 等dagger必须的文件， 实现dagger完全解耦


1: 参考Spring后端注解思路，自定义注解自动生成component, module 等dagger必须的文件， 免去每次都需手动添加注入对象等，可以在无需知道dagger知识的背景下直接使用注入， 
   实现dagger完全解耦
   
2: 采用MVP + Rxjava + Dagger + Retrofit + AutoWire(自动注解). 基本三层架构. 

   I(Interface)             BaseView              BasePresenter             BaseModel
      |                       |       _ _ _ _ _ _ _      |                      |
      |                       |      |    Dagger   |     |                      |
      |                       |      |  <==解耦==>  |     |                      |
      |                       |      |             |     |                      |
      |                       |      |             |     |                      |
      |                       |      |_ AutoWire_ _|     |                      |       [Note: AutoWire再实现dagger的完全解耦]
      |                       |                          |                      |
      |                       |                          |                      | 
      |                       |                          |                      |
   A(Abstract)          BaseRxActivity            BaseRxPresenter        BaseRxModel
      |                  (BaseRxFragment)              |                      |
      |                       |                        |                      |
      |                  (BaseListActivity等)                                 
      |                       |                        |                      |
      |                       |                        |                      |
   B(Business)            BusinessActivity        BusinessPresenter      BusinessModel
   
   
   
   
   ![arch_image2](https://raw.githubusercontent.com/zheng03/dagger2/raw/master/arch.png?raw=true)
  
  
  ![arch image](http://github.com/zheng03/dagger2/raw/master/arch.png?raw=true) 
  
   
   
 3:
