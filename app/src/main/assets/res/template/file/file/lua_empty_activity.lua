require "import"

--import base java classes,if unused,can remove it

import "android.app.*"
import "android.os.*"
import "android.widget.*"
import "android.view.*"


--activity.setTitle('MyLuaApp')
activity.setTheme(require "autotheme"())

activity.setContentView(loadlayout("layout"))

--write your lua code in it