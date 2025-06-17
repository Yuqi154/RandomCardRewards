# 简要教程~

- 打开一个选择界面  
  `/randomcardrewards rewards <pool> <count> <player>`
- 列出所有卡牌池  
  `/randomcardrewards list_pools`

# 数据驱动~

- 本模组完全由数据驱动，包括卡片，卡片池
- 所有数据以配方形式储存（因为可以自动同步到客户端），允许/reload重载
- 数据应该位于 `data/randomcardrewards/recipes` 下

## 添加一个卡牌

```json
{
  "type": "randomcardrewards:card",
  "content": {
    "type": "effect",
    "content": "minecraft:strength",
    "i1": 10,
    "i2": 1000
  },
  "id": "randomcardrewards:test",
  "meta": {
    "descriptionKey": "test",
    "id": "randomcardrewards:test",
    "nameKey": "test",
    "texture": "randomcardrewards:test"
  }
}
```
- type 配方类型，填入 `randomcardrewards:card`
- content 卡牌内容
  - type 卡牌类型，允许值 `none` `effect` `item` `command`
  - content 内容，type为 `effect` 时，填入效果ID，type为 `item` 时，填入物品ID，type为 `command` 时，填入命令
  - i1 额外数值
    - type 为 `effect` 时，i1 效果强度
    - type 为 `item` 时，i1 物品数量
  - i2 额外数值
    - type 为 `effect` 时，i2 效果持续时间
- id 卡牌ID，必须唯一
- meta 卡牌元数据
  - descriptionKey 卡牌描述本地化键值 描述会以tooltip形式显示在卡牌上
  - id 卡牌ID，与上面相同
  - nameKey 卡牌名称本地化键值
  - texture 卡牌纹理

## 添加一个卡牌池

```json
{
  "type": "randomcardrewards:card_pool",
  "id": "randomcardrewards:test_pool",
  "pool": [
    "randomcardrewards:test",
    "randomcardrewards:test1",
    "randomcardrewards:test2",
    "randomcardrewards:test3"
  ]
}
```
- type 配方类型，填入 `randomcardrewards:card_pool`
- id 卡牌池ID，必须唯一
- pool 卡牌池列表，填入卡牌ID

## 使用datagen生成~

去看`org/hiedacamellia/randomcardrewards/data/provider/RCRRecipeProvider.java`

# 卡牌事件~

- `CardInvokeEvent.Pre` 事件在卡牌被触发前调用
  - `RCRCard` event.getCard() 获取卡牌
  - `Player` event.getPlayer() 获取玩家
  - event.setCanceled(true) 取消事件
- `CardInvokeEvent.Post` 事件在卡牌被触发后调用
  - `RCRCard` event.getCard() 获取卡牌
  - `Player` event.getPlayer() 获取玩家

# 卡牌数据结构~

- RCRCard
  - `ResourceLocation` `id` 卡牌ID
  - `String` `nameKey` 卡牌名称本地化键值
  - `String` `descriptionKey` 卡牌描述本地化键值
  - `ResourceLocation` `texture` 卡牌纹理
  - `CardContent` `content` 卡牌内容
    - `CardType` `type` 卡牌类型
      - 枚举 `NONE` `EFFECT` `ITEM` `COMMAND`
    - `String` `content` 内容
    - `int` `i1` 额外数值
    - `int` `i2` 额外数值

# kubejs part~

- 使用API  
  看 `org/hiedacamellia/randomcardrewards/api/RandomCardRewardsAPI.java` [跳转](src/main/java/org/hiedacamellia/randomcardrewards/api/RandomCardRewardsAPI.java)

- TypeWrapper  
  目前可以从文本直接变成RCRCard和CardPool  
  或者使用`RCRCard.of()`, `CardPool.of()`
- 怎么在kubejs里让玩家打开界面
  ```javascript
  let poolid = RandomCardRewardsAPI.createTmpCardPoolFromPoolRandomly("your_pool",<count>)
  RandomCardRewardsAPI.rewardPlayerTmpPool(<player>, poolid);
  ```
  每个创建临时卡牌池的函数都可以加一个是否在使用后自动移除临时卡牌池的参数，默认为`false`.