# Spring_Boot_Application

## 前言

这是学习SpringBoot时的一些笔记，主要是一些常用的注解和配置，以及一些常用的工具类的使用方法以及Demo。

## 遇到的小问题
1. 子模块的pom文件的<relativePath>元素
- 在Maven的POM（Project Object Model）文件中，
<relativePath> 元素通常用于指定父POM文件的相对路径。
它对于多模块项目或项目继承关系中的父子项目非常有用。
- 然而，如果是父子模块之间本质上也是没有强依赖关系的多模块项目，
只是父模块管理子模块，子模块可以继承父模块的一些配置，比如版本号等，
就不需要这个元素了


