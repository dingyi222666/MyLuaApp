---
--- Generated by EmmyLua(https://github.com/EmmyLua)
--- Created by admin.
--- DateTime: 2021/1/23 1:54
--- The Lua no use Activity,you can't use some android api in it

console = require "console" --use console

function sendClassToLua(class, name)
  _G[tostring(name)] = class
end

function build(path, toPath)
  path, toPath = tostring(path), tostring(toPath)
  local _, suffix = string.match(path, '/(.+)%.(.+)$')
  if (suffix == "lua") then
    local path, errorMsg = console.build(path)
    if path then
      os.rename(path, toPath)
      os.remove(path)
    else
      return errorMsg
    end
  else
    local path, errorMsg = console.build_aly(path)
    if path then
      os.rename(path, toPath:gsub("aly$", "lua"))
      os.remove(path)
      os.remove(toPath)
    else
      return errorMsg
    end
  end
  return true
end

