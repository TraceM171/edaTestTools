package g171.edalabtools

import com.github.ajalt.clikt.core.NoOpCliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.output.CliktHelpFormatter
import com.github.ajalt.clikt.parameters.options.versionOption
import g171.edalabtools.decompiler.Decompiler
import g171.edalabtools.generator.Generator
import g171.edalabtools.inspector.Inspector

class EdaLabTools : NoOpCliktCommand(
    name = "elt",
    help = """EdaLabTools is a group of command line tools related to
        EDA laboratory tests, 2nd Course, UPV-Computer Science

        This software is provided as is, without any guarantees"""
) {
    init {
        context {
            helpFormatter = CliktHelpFormatter(
                showDefaultValues = true,
                showRequiredTag = true
            )
        }
        versionOption(VERSION_NAME)
    }
}

fun main(args: Array<String>) = EdaLabTools()
    .subcommands(Decompiler(), Inspector(), Generator())
    .main(args)