require 'httparty'
require 'nokogiri'


class NameGeneratorUrl
  include HTTParty
  base_uri 'http://www.behindthename.com/random'
end


class RegisterUrl
  include HTTParty
   base_uri 'http://kitchensink-ogm.rhcloud.com'
  #base_uri 'http://localhost:8080/kitchensink-ogm'
end

1.upto(10) {

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


