# 关于mj逆天数字显示

mj的药水等级数字显示有这几个地方  

### 语言文件
#### "potion.potency." 
```json
  "potion.potency.0": "",
  "potion.potency.1": "II",
  "potion.potency.2": "III",
  "potion.potency.3": "IV",
  "potion.potency.4": "V",
  "potion.potency.5": "VI",
```
从零开始，零作为第一级，一级不显示

#### "enchantment.level."
```json
  "enchantment.level.1": "I",
  "enchantment.level.2": "II",
  "enchantment.level.3": "III",
  "enchantment.level.4": "IV",
  "enchantment.level.5": "V",
  "enchantment.level.6": "VI",
  "enchantment.level.7": "VII",
  "enchantment.level.8": "VIII",
  "enchantment.level.9": "IX",
  "enchantment.level.10": "X",
```
从一开始正常算级（最正常的一集）

### 代码

#### EffectRenderingInventoryScreen.class
#### PotionUtils.class
