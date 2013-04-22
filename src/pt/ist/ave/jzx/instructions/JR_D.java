package pt.ist.ave.jzx.instructions;

public class JR_D extends Instruction {

	public JR_D(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		int m_pc16 = _cpu.getM_pc16();
		_cpu.setM_pc16(_cpu.add16(m_pc16, (byte) _cpu.getM_memory().read8(m_pc16) + 1));
		_cpu.setM_x8(_cpu.getM_pc16() >> 8);
	}

	@Override
	public int incTstates() {
		return 12;
	}

}
