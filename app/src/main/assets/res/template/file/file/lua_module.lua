require "import"

local base = {}

--private method(function)
local function test(a, b)
  return a * b
end

--public method(function)
base.test = function(a, b)
  return test(a, b)
end

base.new = function(self, ...)
  local new_table = {}
  --parse arg
  new_table['arg'] = select(1, ...)
  ---
  setmetatable(new_table, { __index = self })
  return new_table
end

--construction method(function)
base.__call = function(self, ...)
  return self:new(...)
end

setmetatable(base, base)

--return class(table)
return base