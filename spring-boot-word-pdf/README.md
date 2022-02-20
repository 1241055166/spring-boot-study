word转pdf与pdf转word
1： 在项目的根目录下新建一个lib包，把该jar包放入到下面  aspose-words-15.8.0-jdk16
2：在pom.xml加入依赖
<dependency>
<groupId>com.aspose</groupId>
<artifactId>aspose-words</artifactId>
<version>15.8.0</version>
<scope>system</scope>
<systemPath>${project.basedir}/lib/aspose-words-15.8.0-jdk16.jar</systemPath>
</dependency>

3： 在resources下面添加 License.xml
<License>
<Data>
<Products>
<Product>Aspose.Total for Java</Product>
<Product>Aspose.Words for Java</Product>
</Products>
<EditionType>Enterprise</EditionType>
<SubscriptionExpiry>20991231</SubscriptionExpiry>
<LicenseExpiry>20991231</LicenseExpiry>
<SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber>
</Data>
<Signature>
sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=
</Signature>
</License>

4：
