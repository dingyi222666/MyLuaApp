local _g = _G
local metatable_g = {
  __index = function(self, key)
    return _g.rawget(_g, key) or function(...)
      local arg = { ... }

      local ext = _g.rawget(self, "ext")

      for _, v in _g.ipairs(arg) do
        if _g.type(v) == "string" then
          local a = _g.string.template(v, ext or self)
          arg[_] = a
        end
      end

      if #arg == 1 then
        arg = table.unpack(arg)
      end

      return { type = key, value = arg }
    end
  end
}

local Log = luajava.bindClass("android.util.Log")

function getDefaultProguardFile(file)
  return "/data/data/com.dingyi.MyLuaApp/files/res/build/" .. file
end

function project(name)
  return { type = "project", value = name:match(":(.+)") }
end

function include(name)
  _G.includes = includes or {}
  table.insert(includes, name:match(":(.+)"))
end

local File = luajava.bindClass "java.io.File"

local function forEachDir(path, tab, types)
  local list = File(path).listFiles()

  for n = 1, #list do
    local file = list[n]
    local path = file.path
    local name = file.name
    if (file.isFile()) then
      for _, v in ipairs(types) do
        if name:match(v) then
          table.insert(tab, path)
          goto CONTINUE
        end
      end
    else
      forEachDir(path, tab, types)
    end
    :: CONTINUE ::
  end
end

function string.split(input, delimiter)
  input = tostring(input)
  delimiter = tostring(delimiter)
  if (delimiter == "") then
    return false
  end
  local pos, arr = 0, {}
  for st, sp in function()
    return string.find(input, delimiter, pos, true)
  end do
    table.insert(arr, string.sub(input, pos, st - 1))
    pos = sp + 1
  end
  table.insert(arr, string.sub(input, pos))
  return arr
end

function fileTree(dir, types)
  local project_dir = getNowProjectDir()
  local target_dir = project_dir .. "/" .. dir
  local file = File(target_dir)
  Log.e("fuck", print_dump(types))

  for k, v in pairs(types) do
    types[k] = ".%." .. v:sub(1) .. "$"
  end

  if (file.exists()) then
    local fileTable = {}
    forEachDir(target_dir, fileTable, types)
    return { type = "file", fileTable }
  end
end

function string.template(str, tab)
  return string.gsub(str, "%$%{(.-)%}", function(key)
    return tab[key]
  end)
end

--[[
print_dump是一个用于调试输出数据的函数，能够打印出nil,boolean,number,string,table类型的数据，以及table类型值的元表
参数data表示要输出的数据
参数showMetatable表示是否要输出元表
参数lastCount用于格式控制，用户请勿使用该变量
]]
function print_dump(data, showMetatable, lastCount, t)
  local t = t or {}
  if type(data) ~= "table" then
    --Value
    if type(data) == "string" then
      table.insert(t, "\"" .. data .. "\"")
    else
      table.insert(t, tostring(data))
    end
  else
    --Format
    local count = lastCount or 0
    count = count + 1
    table.insert(t, "{\n")
    --Metatable
    if showMetatable then
      for i = 1, count do
        io.write("\t")
      end
      local mt = getmetatable(data)
      table.insert(t, "\"__metatable\" = ")
      print_dump(mt, showMetatable, count, t)    -- 如果不想看到元表的元表，可将showMetatable处填nil
      table.insert(t, ",\n")     --如果不想在元表后加逗号，可以删除这里的逗号
    end
    --Key
    for key, value in pairs(data) do
      for i = 1, count do
        io.write("\t")
      end
      if type(key) == "string" then
        table.insert(t, "\"" .. key .. "\" = ")
      elseif type(key) == "number" then
        table.insert(t, "[" .. key .. "] = ")
      else
        table.insert(t, tostring(key))
      end
      print_dump(value, showMetatable, count, t) -- 如果不想看到子table的元表，可将showMetatable处填nil
      table.insert(t, ",\n")     --如果不想在table的每一个item后加逗号，可以删除这里的逗号
    end
    --Format
    for i = 1, lastCount or 0 do
      io.write("\t")
    end
    table.insert(t, "}")
  end
  --Format
  if not lastCount then
    table.insert(t, "\n")
  end
  return table.concat(t)
end


function forEachTable(t)
  local clone_t = table.clone(t)
  for i, v in pairs(clone_t) do
    if type(v) == "table" then
      if v.type ~= null then
        local value = v.value
        t[i] = null
        local key = t[v.type] or {}
        table.insert(key, value)
        t[v.type] = key
        if type(v.value) == "table" then
          forEachTable(value)
        end
      else
        forEachTable(v)
      end
    end
  end
end

function runScript(path)
  local empty_table = {
    rootProject = {}
  }
  setmetatable(empty_table, metatable_g)

  Log.e("fuck", path)

  loadfile(path, "bt", empty_table)()

  forEachTable(empty_table)

  _G.empty_table = empty_table

end

function putScriptValue(key, value)
  local t = string.split(key, ".")
  local result = _G
  for _, v in ipairs(t) do
    result = result[v] or result
  end
  result[t[#t]] = value
end

function getScriptValue(key)
  local t = string.split(key, ".")
  local result = _G.empty_table
  Log.e("test", print_dump(result))
  for _, v in ipairs(t) do

    result = result[v]
  end
  Log.e("test", print_dump(result))
  return result
end
