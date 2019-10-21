#!/bin/sh

if ! [ -d drivers/ ]; then
  mkdir "drivers"
  cd drivers/
else
  cd drivers/
fi

show_menu() {
  normal=$(echo "\033[m")
  menu=$(echo "\033[36m")   #Blue
  number=$(echo "\033[33m") #yellow
  bgred=$(echo "\033[41m")
  fgred=$(echo "\033[31m")
  printf "\n${menu}============================================================================${normal}\n"
  printf "${menu}Please select your operating system:${normal}"
  printf "\n${menu}============================================================================${normal}\n"
  printf "${menu}${number} 1)${menu} Windows ${normal}\n"
  printf "${menu}${number} 2)${menu} Linux ${normal}\n"
  printf "${menu}${number} 3)${menu} MacOS ${normal}\n"
  printf "${menu}============================================================================${normal}\n"
  printf "Please, enter a menu option, or enter ${fgred}x${normal} to exit: "
  read opt
}

option_picked() {
  msgcolor=$(echo "\033[01;31m") # bold red
  normal=$(echo "\033[00;00m")   # normal white
  message=${@:-"${normal}Error: No message passed"}
  printf "${msgcolor}${message}${normal}\n"
}

clear
show_menu
while [ $opt != '' ]; do
  if [ $opt = '' ]; then
    exit
  else
    case $opt in
    1)
      clear
      option_picked "Download WebDrivers for Windows"
        # ChromeDriver
        curl -LO https://chromedriver.storage.googleapis.com/77.0.3865.40/chromedriver_win32.zip
        unzip chromedriver_win32.zip
        mv chromedriver.exe chromedriver_win.exe
        rm chromedriver_win32.zip
        # Mozilla GeckoDriver
        curl -LO https://github.com/mozilla/geckodriver/releases/download/v0.25.0/geckodriver-v0.25.0-win64.zip
        unzip geckodriver-v0.25.0-win64.zip
        mv geckodriver.exe geckodriver_win.exe
        rm geckodriver-v0.25.0-win64.zip
        # Opera
        curl -LO https://github.com/operasoftware/operachromiumdriver/releases/download/v.76.0.3809.132/operadriver_win64.zip
        unzip operadriver_win64.zip
        cd operadriver_win64
        mv operadriver.exe operadriver_win.exe
        rm sha512_sum
        cd ..
        mv operadriver_win64/* .
        rmdir operadriver_win64
        rm operadriver_win64.zip
      exit
      ;;
    2)
      clear
      option_picked "Download WebDrivers for Linux Ubuntu"
        #ChromeDriver
        curl -LO https://chromedriver.storage.googleapis.com/77.0.3865.40/chromedriver_linux64.zip
        unzip chromedriver_linux64.zip
        mv chromedriver chromedriver_linux
        rm chromedriver_linux64.zip
        #Mozilla GeckoDriver
        curl -LO https://github.com/mozilla/geckodriver/releases/download/v0.25.0/geckodriver-v0.25.0-linux64.tar.gz
        tar -xvf geckodriver-v0.25.0-linux64.tar.gz
        mv geckodriver geckodriver_linux
        rm geckodriver-v0.25.0-linux64.tar.gz
        # Opera
        curl -LO https://github.com/operasoftware/operachromiumdriver/releases/download/v.76.0.3809.132/operadriver_linux64.zip
        unzip operadriver_linux64.zip
        cd operadriver_linux64
        mv operadriver operadriver_linux
        rm sha512_sum
        cd ..
        mv operadriver_linux64/* .
        rmdir operadriver_linux64
        rm operadriver_linux64.zip
      exit
      ;;
    3)
      clear
      option_picked "Download WebDrivers for MacOS"
        #ChromeDriver
        curl -LO https://chromedriver.storage.googleapis.com/77.0.3865.40/chromedriver_mac64.zip
        unzip chromedriver_mac64.zip
        mv chromedriver chromedriver_mac
        rm chromedriver_mac64.zip
        #Mozilla GeckoDriver
        curl -LO https://github.com/mozilla/geckodriver/releases/download/v0.25.0/geckodriver-v0.25.0-macos.tar.gz
        tar -xvf geckodriver-v0.25.0-macos.tar.gz
        mv geckodriver geckodriver_mac
        rm geckodriver-v0.25.0-macos.tar.gz
        # Opera
        curl -LO https://github.com/operasoftware/operachromiumdriver/releases/download/v.76.0.3809.132/operadriver_mac64.zip
        unzip operadriver_mac64.zip
        cd operadriver_mac64
        mv operadriver operadriver_mac
        rm sha512_sum
        cd ..
        mv operadriver_mac64/* .
        rmdir operadriver_mac64
        rm operadriver_mac64.zip
      exit
      ;;
    x)
      exit
      ;;
    \n)
      exit
      ;;
    *)
      clear
      option_picked "You have chosen the wrong operating system! Please choose again!"
      show_menu
      ;;
    esac
  fi
done