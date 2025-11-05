require 'spec_helper'

RSpec.describe HttpClient do
  it 'returns 404 for not found (using VCR cassette)' do
    VCR.use_cassette('not_found') do
      response = HttpClient.get_status('/status/404')
      expect(response.code.to_i).to eq(404)
    end
  end
end
