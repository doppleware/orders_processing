MAC:

docker run --rm -it -e GOOS=darwin -u "$(id -u):$(id -g)" -v "${PWD}:/xk6" \
  grafana/xk6 build v0.45.1 \
  --with github.com/grafana/xk6-faker@latest --with github.com/mostafa/xk6-kafka@v0.26.0

WINDOWS:

For the native Windows binary if you're using PowerShell:

docker run --rm -it -e GOOS=windows -u "$(id -u):$(id -g)" -v "${PWD}:/xk6" `
  grafana/xk6 build v0.45.1 --output k6.exe `
  --with github.com/grafana/xk6-faker@latest --with github.com/mostafa/xk6-kafka@v0.26.0
  
For the native Windows binary if you're using cmd.exe:

docker run --rm -it -e GOOS=windows -v "%cd%:/xk6" ^
  grafana/xk6 build v0.45.1 --output k6.exe ^
  --with github.com/grafana/xk6-faker@latest --with github.com/mostafa/xk6-kafka@v0.26.0