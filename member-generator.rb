require 'httparty'
require 'nokogiri'
require 'choice'

Choice.options do
  header 'Application options:'

  separator 'Required:'

  option :app_url, :required => true do
    short '-a'
    long '--app_url=URL'
    desc 'The URL of the application'
  end

  option :member_count, :required => true do
    short '-c'
    long '--member_count=COUNT'
    desc 'How many members to create'
  end

  separator 'Common:'

  option :help do
    short '-h'
    long '--help'
    desc 'Show this message.'
  end
end


class NameGeneratorUrl
  include HTTParty
  base_uri 'http://www.behindthename.com/random'
end


class RegisterUrl
  include HTTParty
  base_uri Choice.choices.app_url
  #base_uri 'http://kitchensink-ogm.rhcloud.com'
  #base_uri 'http://localhost:8080/ogm-kitchensink'
end

1.upto(Choice.choices.member_count.to_i()) {

  params = {"number" => "2",
            "gender" => "m",
            "surename" => "",
            "all" => "yes",
  }

  out = NameGeneratorUrl.get('/random.php', :query => params)
  doc = Nokogiri::HTML(out)

  first_name = doc.xpath('//a[@class="plain"]')[0].content
  last_name = doc.xpath('//a[@class="plain"]')[1].content
  name = first_name << ' ' << last_name
  email = last_name.downcase << '@' << 'mailinator.com'
  number = rand(10 ** 10).to_s.rjust(10, '0')

  out = RegisterUrl.get('/index.jsf')
  out = RegisterUrl.get('/index.jsf', :headers => {'Cookie' => out.headers['Set-Cookie']})
  cookie = out.request.options[:headers]['Cookie']

  doc = Nokogiri::HTML(out)
  faces = doc.xpath('//input[@id="javax.faces.ViewState"]')[0]['value']

  params = {"reg" => "reg",
            "reg:name" => name,
            "reg:email" => email,
            "reg:phoneNumber" => number,
            "reg:register" => 'Register',
            "javax.faces.ViewState" => faces
  }

  RegisterUrl.post('/index.jsf', :body => params, :headers => {'Cookie' => cookie})
}


