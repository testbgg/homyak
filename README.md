# homyak
Tech of Madness
[![Build Status](https://travis-ci.org/testbgg/homyak.svg?branch=master)](https://travis-ci.org/testbgg/homyak)

## Build service

```bash
gradle asm
```


## Credentials

user: admin
password: 123



## add entities to db
POST request /fill-db



### Сборка 
```bash
chmod +x asm.sh

./asm.sh

# рядом в корне появится app.jar
```

### Деплой
```bash
chmod +x push.sh
# в файлике push.sh наверзу пишем версию

# создаем файлик pwd.txt и кладем в него пароль
# а в переменной D_LOGIN -- логин
export D_LOGIN=yattbot


./push.sh
```