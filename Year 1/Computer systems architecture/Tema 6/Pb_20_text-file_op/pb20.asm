;A file name and a text (defined in the data segment) are given. The text contains lowercase letters and spaces. Replace all the letters on even positions with their position. Create a file with the given name and write the generated text to file.

bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,fopen,fclose,fprintf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fopen msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fclose msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fprintf msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data

    file_name db "file.txt",0
    text db "abc cd d a aaafaafasgag agsdgasg  a",0
    len equ $-text
    write_mode db "w",0
    write_format_char db "%c",0
    write_format_int db "%d",0
    file_descriptor dd -1
    char dd 0
    counter dd 0
    

; our code starts here
segment code use32 class=code
    start:
        push dword write_mode
        push dword file_name
        call [fopen]
        add esp,4*2
        
        cmp eax,0
        je Incorrect
        mov [file_descriptor],eax
        
        mov ecx,len
        mov esi,text
        cld
        repeta:
        push ecx
            lodsb
            cbw
            cwde
            mov [char],eax
            push dword [char]
            push dword write_format_char
            push dword [file_descriptor]
            call [fprintf]
            add esp,4*3
            
            lodsb
            cmp al,0
            je Gata
            add dword [counter],2
            cmp al," "
            je Mai_departe
            
            push dword [counter]
            push dword write_format_int
            push dword [file_descriptor]
            call [fprintf]
            add esp,4*3
            jmp Next
            
            Mai_departe:
            cbw
            cwde
            mov [char],eax
            push dword [char]
            push dword write_format_char
            push dword [file_descriptor]
            call [fprintf]
            add esp,4*3
            
            Next:
            pop ecx
            dec ecx
            loop repeta
        Gata:
                    
        push dword [file_descriptor]
        call [fclose]    ; closing the file
        add esp, 4
        
        Incorrect:
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
