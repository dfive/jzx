package pt.ist.ave.jzx.Instructions;

public class DEC_DE extends Instruction {

	public DEC_DE(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
//		dec16de();
		_cpu.dec16de();
		
	}

	@Override
	public int incTstates() {
//		m_tstates += 6;
		return 6;
	}

}
