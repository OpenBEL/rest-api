# BEL Framework REST API Apache Buildr Buildfile
# vim: filetype=ruby:ts=2:sw=2 :
#
# Build settings
settings = Buildr.settings.build
# Build options
options = Buildr.options

# Disables testing (i.e., defer to a build server)
# options.test = false

# Convenient tasks
# buildr cp -> buildr clean package
task :cp => [:clean, :package]
# buildr p -> buildr package
task :p  => [:package]
# buildr c -> buildr clean
task :c  => [:clean]

# TEST CONFIGURATION
settings['junit'] = '4.11'

# ENVIRONMENT CONFIGURATION
if ENV['INTEGRATION_TESTS'] == '1'
  INTEGRATION_TESTS = true
else
  INTEGRATION_TESTS = false
end
if ENV['PERFORMANCE_TESTS'] == '1'
  PERFORMANCE_TESTS = true
else
  PERFORMANCE_TESTS = false
end

deps = Dir['deps/*.jar'].each { |x| "#{Dir.pwd}/#{x}" }
classpath = deps.join(':')

# PRINT CONFIGURATION
print '---', "\n"
print 'Integration tests: ', INTEGRATION_TESTS, "\n"
print 'Performance tests: ', PERFORMANCE_TESTS, "\n"
print '---', "\n"

layout = Layout.new
layout[:source, :main, :java] = 'src'
layout[:source, :test, :java] = 'test'
layout[:source, :main, :resources] = 'resources'
layout[:source, :test, :resources] = 'test'
layout[:target] = 'bin'
layout[:target, :main, :classes] = 'bin'

Project.local_task :deps

define 'rest-api', :layout=>layout do
  project.version = '1.0'
  project.group = 'org.openbel.rest'
  compile.with deps
  default_compile_opts compile

  main = 'org.openbel.rest.main'
  run.using :main => main,
            :java_args => ['-Drebel.restlet_plugin=true']
  package(:tgz).include _('.')

  task :deps => compile do
    deps_path = project.path_to('deps')
    if !File.directory? deps_path
      Dir.mkdir(File.join(deps_path))
    end
    _deps = project.compile.dependencies
    cp _deps.collect { |t| t.to_s }, deps_path
  end

end

# Configures default compilation options (1.7 and all lint checks).
def default_compile_opts(compile)
  compile.options.source = '1.7'
  compile.options.target = '1.7'
  compile.options.lint = 'all'
  compile.options.other = %w{-encoding utf-8}
  compile.options.other = '-XDignore.symbol.file'
end

# Configures default project version
def configure(project)
  # Setup project tests
  if not INTEGRATION_TESTS
    project.test.exclude '*IT'
  end
end

