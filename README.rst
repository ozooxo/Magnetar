========
Magnetar
========

"Magnetar" is a puzzle game to challenge the limit of your organizing skills.

The name of the game means you can "tar" (archive) several "magnets". In astronomy, "Magnetar" also means the kind of neutron star which has extremely strong magnetic field; however, this game is not directly related to that topic.

The game will give you several magnets which stay randomly in the screen, and your task is to move everybody to the gray region in the center of your screen. In the beginning, you can move only one magnet; however, when the north pole and the south pole of two separate magnets are next to each other, the two magnets will be conjoined together, so you are capable move more. You can also use the magnet you can move to push other magnets.

.. image:: https://raw.github.com/ozooxo/Magnetar/master/Screenshot.png
   :height: 600 px
   :width: 800 px
   :scale: 100 %
   :alt: alternate text
   :align: center

Installation and Playing
========================

"Magnetar" is written in Java. To compile it, you will need a Java virtual machine (JVM). This game is made and debugged in OpenJDK version "1.7.0_21" in a 64-bit Ubuntu 13.04; however, it should be capable to run in most other JVMs. You can run the source code by typing:

::

    $ javac *.java
    $ java Magnetar

The ``.jar`` (Java ARchive) file of "Magnetar" can be downloaded at https://sourceforge.net/projects/magnetar/ . In many operation systems, you can execute it like any other executable file. If you fail to do that, you can enter a command

::

    $ java -jar Magnetar-0.1.jar 

After you successfully run the game, you can use cursor keys ``↑``, ``→``, ``↓`` and ``←`` to move the magnets, ``Ctrl-N`` to start a new game, ``Ctrl-B`` to restart the game, and ``Ctrl-W`` to quit the game.

Copyright and License
=====================

This program has been written by Cong-Xin Qiu. It is protected by the `"GNU Lesser Public License"`_ .

.. _"GNU Lesser Public License": http://www.gnu.org/copyleft/lesser.html
